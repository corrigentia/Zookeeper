import org.hyperskill.hstest.dynamic.DynamicTest
import org.hyperskill.hstest.stage.StageTest
import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testing.TestedProgram


class ZookeeperTest : StageTest<String>() {

    private var theEndMessage = "See you later!"
    private var indexArray = arrayOf("0", "1", "2", "3", "4", "5")
    private var animalIndex = mapOf(
            "0" to arrayOf(camel, "camel"),
            "1" to arrayOf(lion, "lion"),
            "2" to arrayOf(deer, "deer"),
            "3" to arrayOf(goose, "goose"),
            "4" to arrayOf(bat, "bat"),
            "5" to arrayOf(rabbit, "rabbit")
    )

    private var tests = arrayOf(
            "1\nexit",
            "3\nexit",
            "5\nexit",
            "0\n2\n4\nexit",
            "0\n1\n2\n3\n4\n5\nexit"
    )

    @DynamicTest(data = "tests")
    fun test(input: String): CheckResult {

        val includedAnimals = input.replace("\nexit", "").split("\n")
        val excludedAnimals = indexArray.filter { element -> !includedAnimals.contains(element) }

        val testedProgram = TestedProgram()
        testedProgram.start()
        val output = testedProgram.execute(input)

        for (includedAnimal in includedAnimals) {
            val animalImage = animalIndex[includedAnimal]?.get(0).toString()
            val animalName = animalIndex[includedAnimal]?.get(1).toString()

            if (!output.contains(animalImage)) {
                return CheckResult.wrong("The $animalName wasn't printed but was expected")
            }
        }

        for (excludedAnimal in excludedAnimals) {
            val animalImage = animalIndex[excludedAnimal]?.get(0).toString()
            val animalName = animalIndex[excludedAnimal]?.get(1).toString()

            if (output.contains(animalImage)) {
                return CheckResult.wrong("The $animalName was printed but wasn't expected")
            }
        }

        if (!output.contains(theEndMessage)) {
            return CheckResult.wrong("You should print '$theEndMessage' at the end of the program")
        }

        return CheckResult.correct()
    }
}