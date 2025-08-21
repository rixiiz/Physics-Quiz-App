# Physics-Quiz-App
A GUI designed to help International Baccalaureate (IB) Physics students revise multiple-choice questions efficiently.

## Features:
- Topic-specific MCQs (can choose up to 8 topics)
- Instant answer feedback
- Score tracking and different timer-based quiz modes
- Allows student to focus on weak areas
- Clean and minimal GUI

## Key insights:
- GUIs are very easy and intuitive to make but take a long time to setup the layout manuelly.
- Libraries like `Apache POI` facilitate the usage of external tools like Excel during program development.
- Learned how MVC in OOP works and how using it to organize the classes is a helpful way to structure code and maintain readability over time or in collaborative environments.
- It is hard to manually manipulate csv files or other databases
- Learned how to teach myself unknown libraries like Apache POI through online documentation
- Leanred how to treat and adapt to my client's needs
- Learned different stages of program development and the need to document it for future team collaborations

## Repo guide:
1. Clone the repo: `git clone https://github.com/rixiiz/Physics-Quiz-App.git`
2. Check `requirements.txt`
3. Download all the `jar` files into a folder (go to `https://poi.apache.org/download.html` `https://logging.apache.org/log4j/2.x/download.html` to download the binary distributions and find all the corresponding `jar` files inside the zip folder)
4. Open project (folder `Product` in `Physics-Quiz-App`) in IntelliJ
5. Go to File → Project Structure → Modules → Dependencies → + → JARs or directories
6. Select all `jar` files previously downloaded in the folder in step 3 → Scope: Compile → Apply → OK.
7. Run Controller.java
