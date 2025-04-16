# Hibernate Notes

Table of contents
- [Join Tables](#JoinTables)
- [Fetch Types](#fetch-types)
- [Cascade Types](#cascade-types)
- [Entity Graph](#entity-graph)

## JoinTables

Example implementations. We have created different entities for different join types so that the queries generated are easily visualizable. 

1. `One-to-one`
2. `One-to-many` - One [AuthorEntity.java](playground/app/src/main/java/com/example/playground/bookstore/model/AuthorEntity.java) to Many [BookEntity.java](playground/app/src/main/java/com/example/playground/bookstore/model/BookEntity.java)
3. `Many-to-many` - [PersonEntity.java](playground/app/src/main/java/com/example/playground/family/model/PersonEntity.java)

## Fetch Types

We can test the behaviour of `LAZY` and `EAGER` in [PersonEntity.java](playground/app/src/main/java/com/example/playground/family/model/PersonEntity.java) and curl this endpoint to observe the difference. 
```shell
curl -X 'POST' \
  'http://localhost:8080/family/person' \
  -H 'accept: */*' \
  -H 'Authorization: Basic YWRtaW5AYmFzaWM6YWRtaW4=' \
 -H 'Content-Type: application/json' \
  -d '{ "name": "father" }'
```

EAGER will fetch all associated entities. In this example, it will fetch parents and children of the associated entities. In a self-referencing relationship, this may end up traversing the entire lineage from root to leaf.
```
[org.hibernate.SQL] select pe1_0.id,pe1_0.name from family.person pe1_0 where upper(pe1_0.name)=upper(?)
[org.hibernate.orm.jdbc.bind] binding parameter (1:VARCHAR) <- [father]
[org.hibernate.SQL] select p1_0.child_id,p1_1.id,p1_1.name from family.tree p1_0 join family.person p1_1 on p1_1.id=p1_0.parent_id where p1_0.child_id=?
[org.hibernate.orm.jdbc.bind] binding parameter (1:BIGINT) <- [2]
[org.hibernate.SQL] select p1_0.child_id,p1_1.id,p1_1.name from family.tree p1_0 join family.person p1_1 on p1_1.id=p1_0.parent_id where p1_0.child_id=?
[org.hibernate.orm.jdbc.bind] binding parameter (1:BIGINT) <- [3]
[org.hibernate.SQL] select c1_0.parent_id,c1_1.id,c1_1.name from family.tree c1_0 join family.person c1_1 on c1_1.id=c1_0.child_id where c1_0.parent_id=?
[org.hibernate.orm.jdbc.bind] binding parameter (1:BIGINT) <- [2]
[org.hibernate.SQL] select p1_0.child_id,p1_1.id,p1_1.name from family.tree p1_0 join family.person p1_1 on p1_1.id=p1_0.parent_id where p1_0.child_id=?
[org.hibernate.orm.jdbc.bind] binding parameter (1:BIGINT) <- [6]
[org.hibernate.SQL] select p1_0.child_id,p1_1.id,p1_1.name from family.tree p1_0 join family.person p1_1 on p1_1.id=p1_0.parent_id where p1_0.child_id=?
[org.hibernate.orm.jdbc.bind] binding parameter (1:BIGINT) <- [1]
```

LAZY will only fetch what is immediately requested.
```
[org.hibernate.SQL] select pe1_0.id,pe1_0.name from family.person pe1_0 where upper(pe1_0.name)=upper(?)
[org.hibernate.orm.jdbc.bind] binding parameter (1:VARCHAR) <- [father]
[org.hibernate.SQL] select c1_0.parent_id,c1_1.id,c1_1.name from family.tree c1_0 join family.person c1_1 on c1_1.id=c1_0.child_id where c1_0.parent_id=?
[org.hibernate.orm.jdbc.bind] binding parameter (1:BIGINT) <- [2]
[org.hibernate.SQL] select p1_0.child_id,p1_1.id,p1_1.name from family.tree p1_0 join family.person p1_1 on p1_1.id=p1_0.parent_id where p1_0.child_id=?
[org.hibernate.orm.jdbc.bind] binding parameter (1:BIGINT) <- [2] 
```

## Cascade Types

The most common cascade types are `CascadeType.MERGE` and `CascadeType.PERSIST`.

## Entity Graph

We can test the behaviour of `EntityGraph` in [PersonRepository.java](playground/app/src/main/java/com/example/playground/family/repository/PersonRepository.java).

Passing `?useEntityGraph=true` will trigger the repo method that uses `EntityGraph`
```shell
curl -X 'POST' \
  'http://localhost:8080/family/person?useEntityGraph=true' \
  -H 'accept: */*' \
  -H 'Authorization: Basic YWRtaW5AYmFzaWM6YWRtaW4=' \
  -H 'Content-Type: application/json' \
  -d '{ "name": "father" }'
```
will produce a single query that does all the required joins
```
[org.hibernate.SQL] select pe1_0.id,c1_0.parent_id,c1_1.id,c1_1.name,pe1_0.name,p1_0.child_id,p1_1.id,p1_1.name from family.person pe1_0 left join family.tree c1_0 on pe1_0.id=c1_0.parent_id left join family.person c1_1 on c1_1.id=c1_0.child_id left join family.tree p1_0 on pe1_0.id=p1_0.child_id left join family.person p1_1 on p1_1.id=p1_0.parent_id where upper(pe1_0.name)=upper(?)
```

Passing `?useEntityGraph=false` or omitting it will trigger the repo method without the `@EntityGraph` annotation.
```shell
curl -X 'POST' \
  'http://localhost:8080/family/person' \
  -H 'accept: */*' \
  -H 'Authorization: Basic YWRtaW5AYmFzaWM6YWRtaW4=' \
  -H 'Content-Type: application/json' \
  -d '{ "name": "father" }'
```
will produce multiple queries
```
[org.hibernate.SQL] select pe1_0.id,pe1_0.name from family.person pe1_0 where pe1_0.name=? 
[org.hibernate.orm.jdbc.bind] binding parameter (1:VARCHAR) <- [father] 
[org.hibernate.SQL] select c1_0.parent_id,c1_1.id,c1_1.name from family.tree c1_0 join family.person c1_1 on c1_1.id=c1_0.child_id where c1_0.parent_id=? 
[org.hibernate.orm.jdbc.bind] binding parameter (1:BIGINT) <- [2] 
[org.hibernate.SQL] select p1_0.child_id,p1_1.id,p1_1.name from family.tree p1_0 join family.person p1_1 on p1_1.id=p1_0.parent_id where p1_0.child_id=? 
[org.hibernate.orm.jdbc.bind] binding parameter (1:BIGINT) <- [2] 
```
