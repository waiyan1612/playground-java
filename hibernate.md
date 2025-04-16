# Hibernate Notes

Table of contents
- [Join Tables](#Join-tables)
- [Inheritance Types and Discriminator Columns](#inheritance-types-and-discriminator-columns)
- [Fetch Types](#fetch-types)
- [Cascade Types](#cascade-types)
- [Entity Graph / N+1 Problem](#entity-graph)
- [Sequence Generators](#sequence-generators)

## Join Tables

Example implementations. We have created different entities for different join types so that the queries generated are easily visualizable. 

1. `One-to-one` - One [AuthorEntity.java](playground/app/src/main/java/com/example/playground/bookstore/model/AuthorEntity.java) to One [AuthorBioEntity.java](playground/app/src/main/java/com/example/playground/bookstore/model/AuthorBioEntity.java)
    - We can see the usages of `@JoinColumn` and `@PrimaryKeyJoinColumn` in these classes.
2. `One-to-many` - One [AuthorEntity.java](playground/app/src/main/java/com/example/playground/bookstore/model/AuthorEntity.java) to Many [BookEntity.java](playground/app/src/main/java/com/example/playground/bookstore/model/BookEntity.java)
3. `Many-to-many` - [PersonEntity.java](playground/app/src/main/java/com/example/playground/family/model/PersonEntity.java)

## Inheritance Types and Discriminator Columns

`InheritanceType.JOINED` is implemented in [DocumentEntity](playground/app/src/main/java/com/example/playground/id/model/DocumentEntity.java) which is the superclass for [PassportEntity](playground/app/src/main/java/com/example/playground/id/model/PassportEntity.java) and [DrivingLicenseEntity](playground/app/src/main/java/com/example/playground/id/model/DrivingLicenseEntity.java). Also see the `@DiscriminatorColumn` and `@DiscriminatorValue` in these classes.

`InheritanceType.SINGLE_TABLE` and `InheritanceType.TABLE_PER_CLASS` are not implemented since they are more straightforward.

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

### CascadeType.PERSIST

See `addBookAndAuthor` in [BookstoreService](playground/app/src/main/java/com/example/playground/bookstore/service/BookstoreService.java) and the `author` field of [BookEntity](playground/app/src/main/java/com/example/playground/bookstore/model/BookEntity.java) for the implementation.

Sample Request
```shell
curl -X 'POST' \
  'http://localhost:8080/bookstore/add-book-and-author' \
  -H 'accept: */*' \
  -H 'Authorization: Basic YWRtaW5AYmFzaWM6YWRtaW4=' \
  -H 'Content-Type: application/json' \
  -d '{ "authorName": "New Author", "bookTitle": "New Book" }'
```

### CascadeType.MERGE

See `updateFirstBookOfAuthor` in [BookstoreService](playground/app/src/main/java/com/example/playground/bookstore/service/BookstoreService.java), the `books` field of [AuthorEntity](playground/app/src/main/java/com/example/playground/bookstore/model/AuthorEntity.java) and the `author` field of [BookEntity](playground/app/src/main/java/com/example/playground/bookstore/model/BookEntity.java) for the implementation.

Request where the author has book. The fetched object is tracked in Hibernate's context. The object is updated even without CascadeType.MERGE in AuthorEntity.
```shell
curl -X 'POST' \
  'http://localhost:8080/bookstore/update-first-book-of-author' \
  -H 'accept: */*' \
  -H 'Authorization: Basic YWRtaW5AYmFzaWM6YWRtaW4=' \
  -H 'Content-Type: application/json' \
  -d '{ "authorName": "Charles", "bookTitle": "Gone with the Wind" }'
```

Request where the author does not have any book. A new object is created outside Hibernate's context. Without CascadeType.MERGE in AuthorEntity, the new object is not persisted.  
```shell
curl -X 'POST' \
  'http://localhost:8080/bookstore/update-first-book-of-author' \
  -H 'accept: */*' \
  -H 'Authorization: Basic YWRtaW5AYmFzaWM6YWRtaW4=' \
  -H 'Content-Type: application/json' \
  -d '{ "authorName": "Mitchell", "bookTitle": "Gone with the Wind" }'
```


## Entity Graph

`EntityGraph` is a solution for N+1 problem. See [PersonRepository.java](playground/app/src/main/java/com/example/playground/family/repository/PersonRepository.java) for the implementation.

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
will produce N+1 queries
```
[org.hibernate.SQL] select pe1_0.id,pe1_0.name from family.person pe1_0 where pe1_0.name=? 
[org.hibernate.orm.jdbc.bind] binding parameter (1:VARCHAR) <- [father] 
[org.hibernate.SQL] select c1_0.parent_id,c1_1.id,c1_1.name from family.tree c1_0 join family.person c1_1 on c1_1.id=c1_0.child_id where c1_0.parent_id=? 
[org.hibernate.orm.jdbc.bind] binding parameter (1:BIGINT) <- [2] 
[org.hibernate.SQL] select p1_0.child_id,p1_1.id,p1_1.name from family.tree p1_0 join family.person p1_1 on p1_1.id=p1_0.parent_id where p1_0.child_id=? 
[org.hibernate.orm.jdbc.bind] binding parameter (1:BIGINT) <- [2] 
```

## Sequence Generators

See `@SequenceGenerator` in [BookEntity.java](playground/app/src/main/java/com/example/playground/bookstore/model/BookEntity.java) and
the DDLs in [V5__Create_insert_bookstore_author_and_book.sql](playground/db/migration/V5__Create_insert_bookstore_author_and_book.sql).
