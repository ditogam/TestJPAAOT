# TestJPAAOT

This project demonstrates an issue in Spring Data JPA AOT code generation where some derived queries are generated differently compared to normal JVM runtime.

Repository with the problem:
UserRepository

Problematic methods:
- deleteByEmailAndIdIsNotNull(String email)
- findDistinctUserIdsByStreetLike(String street) (known issue in Spring Data JPA #4094)

Working method:
- deleteByEmail(String email)

The project shows that deleteByEmail is generated correctly under AOT, but deleteByEmailAndIdIsNotNull is not.

---

## Build (JVM)

```
./gradlew clean test
```

## Build (native / AOT)

```
export GRAALVM_HOME=$HOME/.sdkman/candidates/java/current
export JAVA_HOME=$GRAALVM_HOME
./gradlew clean nativeCompile
```

The AOT-generated repositories can be inspected under:

```
build/generated/aotSources/
```

Run the native executable:

```
./build/native/nativeCompile/TestJPAAOT
```

This reproduces the incorrect AOT repository behavior.
