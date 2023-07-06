# IntelliCheckers
This README provides an overview of CRC (Cyclic Redundancy Check) and Hamming Code, two popular error detection techniques used in data transmission and storage systems.

### CRC (Cyclic Redundancy Check)
CRC is an error detection technique commonly used in digital networks and storage systems. It involves appending a checksum, or CRC code, to the data being transmitted. The CRC code is generated based on a mathematical algorithm, typically using a polynomial division. The receiving system can then use the same algorithm to calculate its own CRC code for the received data and compare it to the transmitted CRC code. If the calculated CRC code matches the transmitted CRC code, it indicates that the data is likely intact and free from errors.

The CRC algorithm works by treating the data as a binary number and performing polynomial division on it with a predetermined divisor. The remainder obtained from the division becomes the CRC code. At the receiver's end, the received data is divided by the same divisor, and the remainder is compared with the transmitted CRC code. If the remainder is zero, it indicates that the data is error-free. If the remainder is non-zero, it suggests the presence of errors in the data.

CRC provides a reliable and efficient method for detecting errors in data transmission and storage. It is widely used in protocols like Ethernet, USB, and Wi-Fi, as well as in storage systems like hard drives and optical discs.

### Hamming Code
Hamming Code is an error detection and correction technique used to detect and correct single-bit errors in data. It adds additional redundant bits, known as parity bits, to the original data. The parity bits are calculated based on specific rules defined by the Hamming Code algorithm. The receiver can then use these parity bits to identify and correct single-bit errors that may have occurred during transmission.

The Hamming Code algorithm ensures that the parity bits are positioned in such a way that each bit is responsible for checking a specific set of bits in the original data. By analyzing the parity bits and comparing them to the received data, the receiver can determine if any single-bit errors have occurred and correct them accordingly.

Hamming Code is particularly useful in systems where the probability of single-bit errors is relatively high, such as in memory systems or communication channels with high noise levels. It provides a means of error detection and correction while minimizing the amount of additional data required.

In conclusion, both CRC and Hamming Code are powerful error detection techniques used in various systems. CRC provides efficient error detection by appending a CRC code to the data, while Hamming Code offers error detection and correction capabilities by introducing parity bits. By utilizing these techniques, data integrity and reliability can be significantly improved in a wide range of applications.

## Requirements and configuration
- To run local Tomcat server You need to download the newest version from: https://tomcat.apache.org/download-90.cgi.

- Clone repository to your system and open Intellij IDEA project (File->Open) or clone it with IDE,to do it run File->New->Project from Version Control and paste a link to project's GitHub in URL form.

- Add path to Tomcat server:
  1. Select current configuration and click on "Edit configurations...",
  2. Select Tomcat server You are about to use (can change "Name" if needed),
  3. (Optional) Change default browser in "After launch" if you don't have Safari browser installed,
  3. In "Application server" click on "Configure..." button,
  4. In "Tomcat Home" select path to downloaded in step 1. Tomcat server directory,
  5. Select "OK" to exit.
- Run Tomcat server (warning: You need IDEA Ultimate subscription to run lastly saved configuration, if You don't read this: https://stackoverflow.com/questions/22047860/tomcat-in-intellij-idea-community-edition):

## Application
1. To see results, open "http://localhost:8080/Projekt_SOB_war/index.jsp" in Your browser ("Project_SOB_war" might be changed in "Edit configurations..." tab).
2. To check Hamming code error detection select "Hamming":
   - enter word which You want to check (example: "project"),
   - You can add random error by clicking on "Pokaż błędy" or click on bit You want to change (it's value will change to opposite),
   - click "Wyślij" button,
   - if You entered error they will be seen on new tab (changing 1,2,4 or 8th column won't change message meaning), if there are no errors You will see expected information.
3. To check CRC code error detection select "CRC":
    - enter bit message You want to send (example: 100011011)
    - enter bit divisor needed to calculate REM (example: 1001)
    - click "Generuj" button,
    - now You can add errors to message: add random error with "Wypełnij losowo" button or select bits You want to change (it's value will change to opposite),
    - send message with "Wyślij" button
    - if message is corrupted You will see differences to orginal message, if not the message "Dane poprawne" will be shown.