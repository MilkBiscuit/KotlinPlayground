package interview

open class Animal
class Dog : Animal()

fun Animal.speak() = println("Animal speaking")
fun Dog.speak() = println("Dog barking")

fun main() {
    val animal: Animal = Dog()
    animal.speak()
}
