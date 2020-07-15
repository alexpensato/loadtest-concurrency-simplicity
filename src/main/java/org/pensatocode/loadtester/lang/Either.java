package org.pensatocode.loadtester.lang;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This class was extracted from: https://medium.com/swlh/all-you-ever-wanted-to-know-about-java-exceptions-cfae1dff8504
 * <br/>
 * The basic idea is handling any exceptional control flow in the implementation, instead of propagating an exception.
 * By choosing a more complex return value, we can communicate any problems back to the callee.
 * They might have to check the return value more thoroughly, but the scope of the exception changed.
 * In many cases, we might no longer need a throws anymore.
 * <br/>
 * Other programming languages, even JVM-based ones like Scala, have specialized types for error handling,
 * like Either[+A, +B], which can be either the one, or the other type.
 * <br/>
 * Java doesn’t have such a flexible type. The closest multi-state type is Optional, although it doesn't convey
 * any additional information. But with its help we can build our own Java-version of Either<L, R> class.
 * <br/>
 * Java can now return an Either.left(errorInfo) or Either.right(successValue)
 * and it can be consumed in a functional fashion.
 * <br/>
 * Example:
 * <code>
 *      Either<Integer, String> result = doWork();
 *      result.apply(errorCode -> log.error("Error {} occuring during work", errorCode),
 *                   value -> log.info("Success: {}", value));
 * </code>
 *
 * @param <L> error info
 * @param <R> success value
 */
public class Either<L, R> {

    public static <L, R> Either<L, R> left(L value) {
        return new Either<>(value, null);
    }

    public static <L, R> Either<L, R> right(R value) {
        return new Either<>(null, value);
    }

    private final Optional<L> left;
    private final Optional<R> right;

    private Either(L left, R right) {
        this.left = Optional.ofNullable(left);
        this.right = Optional.ofNullable(right);
    }

    private Either(Optional<L> left, Optional<R> right) {
        this.left = left;
        this.right = right;
    }

    public <T> T map(Function<? super L, ? extends T> mapLeft, //
                     Function<? super R, ? extends T> mapRight) {

        return this.left.<T> map(mapLeft) //
                .orElseGet(() -> this.right.map(mapRight).get());
    }

    public <T> Either<T, R> mapLeft(Function<? super L, ? extends T> leftFn) {
        return new Either<>(this.left.map(leftFn), //
                this.right);
    }

    public <T> Either<L, T> mapRight(Function<? super R, ? extends T> rightFn) {
        return new Either<>(this.left, //
                this.right.map(rightFn));
    }

    public void apply(Consumer<? super L> leftFn, Consumer<? super R> rightFn) {
        this.left.ifPresent(leftFn);
        this.right.ifPresent(rightFn);
    }
}
