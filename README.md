# POS Application

This repository contains the source code for the POS application using JavaFX and MySQL.

## Getting Started

Follow these instructions to set up and run the project on your local machine.

### Prerequisites

* Java 17 or higher
* MySQL 8.0 or higher
* JavaFX
* Scene Builder

### Installation

1. Clone the repository
   ```sh
   git clone https://github.com/NovqiGarrix/pos-javafx
    ```
2. Import the database
    ```sh
    mysql -u root -p < migrations.sql
    ```

## Usage

Intellij IDEA provided a simple way to run the application. Just click the green arrow button in the top right corner of
the IDE.

## Compile to JAR

1. Open the project in Intellij IDEA
2. Go to `File` > `Project Structure` > `Artifacts`
3. Click the `+` button and select `JAR` > `Empty`
4. Click `OK`
5. Click `OK` again
6. Go to `Build` > `Build Artifacts` > `Build`
7. The JAR file will be generated in the `out` folder

## Create Installer

You have to compile the project to JAR first before creating the installer, because we're going to need the JAR file.

```sh
jpackage --name AppName --input out/artifacts/pos_jar --main-jar pos_javafx.jar --main-class com.novqigarrix.pos.HelloApplication --type app-image
```

Available app image formats:

* `dmg`
* `pkg`
* `rpm`
* `deb`
* `exe`
* `msi`