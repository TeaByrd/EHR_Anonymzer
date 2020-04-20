# EHR_Anonymzer
A tool for german electronic health care (ehr) de-identification / anonymization of private content.

## INTRODUCTION

This program was created for a Java Programming course at B-IT, Bonn, Germany. 
Creators: Alex Hoch, Bryce Fransen, Thomas Lordick

Health records contain a significant amount of data for finding patterns and useful information for future research purposes. However, extracting this information without having a patient’s identity revealed is a time-consuming operation. This program is built for anyone to easily edit an electronic health record to anonymize any identification entities and extract only the necessary data for future analysis.

The EHR Anonymizer allows a user to upload a German health .doc/.docx document for anonymization. It displays the raw text on the left-hand side and a brat viewer of the same text but with labeled entities on the right. Brat is a third party software that runs a Natural Language Processing program to initially annotate potential personal information. Additionally, the user has the ability to tag any word that contains any personal information and saved for annotating future documents.


# REQUIREMENTS

- Java (JDK 11)
- Maven (latest version) (https://maven.apache.org/)
- Download and setup Brat server (https://brat.nlplab.org/downloads.html)
- Download Ubuntu terminal (if needed; https://tutorials.ubuntu.com/tutorial/tutorial-ubuntu-on-windows#0) 


## INSTALLATION

- Run Brat server
- Run the installation file in the EHR directory
```shell
$ ./build.sh
```
- In GUI specify the path to Brat data folder


## TROUBLESHOOTING & FAQ

- How do I edit the document in the Brat viewer?
  - You need to log in to edit.


## MAINTAINERS

Current maintainers:
- Alex Hoch   --   B-IT, University of Bonn
- Bryce Fransen  --  B-IT, University of Bonn
- Thomas Lordick  --  B-IT, University of Bonn

