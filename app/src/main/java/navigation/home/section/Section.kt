package navigation.home.section

data class Section(
    val headerTitle: String,
    val items: List<String> // Or whatever type your items are
)


val sections = listOf(
    Section("Fruits", listOf("Apple", "Banana", "Orange", "Grape", "Kiwi")),
    Section("Vegetables", listOf("Carrot", "Broccoli", "Spinach", "Tomato", "Cucumber")),
    Section("Dairy", listOf("Milk", "Cheese", "Yogurt", "Butter")),
    // Add more sections as needed
)

val myDataSections = listOf(
    Section("Morning", listOf("Breakfast", "Coffee", "News")),
    Section("Afternoon", listOf("Lunch", "Work", "Exercise")),
    Section("Evening", listOf("Dinner", "Relax", "Read")),
    Section("Late Night", listOf("Snack", "Movie", "Sleep"))
)
