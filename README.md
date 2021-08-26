# CSV Query Language IDE

---

[TOC]

---

## Introduction 

### Background

- For the COMP2212: Programming Language Concepts module, a programming langauge was designed that supported the querying of CSV files and an interpreter was written. 
  - This programming langauge is known as **CSV Query Language**.
  - Please refer to the [COMP2212:PLC Coursework repository](https://github.com/cekpowell/comp2212-coursework) for more information on the project, and full documentation of the CSVQL programming langauge.

### Project Description

- The goal of this project was to create an **IDE/Text Editor** for **CSV Query Language**.

- The finished application supports the **creation**, **editing** and **running** of CSVQL programs as well as CSV/text files (i.e., the input data) in a single environment and includes **highlighting** for CSVQL syntax.

<video src="img/intro_vid.mp4" width="600"></video>

---

## Getting Started

*A step-by-step guide on how to run th CSVQL IDE.*

- The CSVQL IDE can be run using the provided `pom.xml` file with the `maven` framework.
- Clone the repo and use the following command to run the application:

```bash
mvn clean javafx:run
```

- On start-up, the application should look like this:

<p align="center"><img src="img/start.png" alt="distributed_file_storage_system" width="650";"/></p> 

---

## Application Structure

*A breakdown of the IDE's application structure.*

- The appplication is broken down into three sections.
  - **Editor**
  - **Table Store**
  - **Terminal**
- Files that are loaded into or created in the application will be displayed as a tab in the **Editor**, where they can be **edited** and **run**.
  - Each Editor Tab contains a toolbar that supports the saving, editing and running (if it is a program) of the file.
- All tables loaded into the system are stored in the **Table Store**, and will be available for CSVQL programs when they arerun.
  - The purpose of the Table Store is to be able to have CSV tables loaded into the system without requiring them to be open in the Editor when a program is being run.
- When running a CSVQL program, the program output is displayed in the **Terminal**.
  - The Terminal contains a toolbar that supports the saving of this output to a file, or copying it to the clipboard.

---

## Usage

*A short guide on the main features of the application.*

### Creating New Files

- 

### Opening Existing Files

#### Open Buttons

- 

#### Drag and Drop

- 

#### Table Store

- 

### Editor Guide

- 

### Running Programs

#### Execution

- 

#### Additional Terminal Features

- 

---

## Additional Langauge Support

*A description of the additional programming langauges supported by the CSVQL IDE*.

- 

---

## Known Issues

*Documented issues with the system that could not be resolved.*

- 

---