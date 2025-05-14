# 💪 MTS Online Payment Automation Tests

This project contains automated tests for verifying the online payment process on the **MTS.by** website.  
The tests are written in Java + Selenium + TestNG, reports are generated using **Allure**, the build is managed via **Maven**, and the execution is automated through **Jenkins**.

---

## 📁 Project Structure

- `src/test/java/` — tests and helper classes
- `logs/` — log files
- `target/allure-results/` — test execution results for Allure
- `target/allure-report/` — generated Allure HTML report

---

## ⚙️ Technologies Used

- **Java 11+**
- **Selenium WebDriver**
- **TestNG**
- **Allure Report**
- **Maven**
- **Jenkins**
- **Log4j2**
- **Telegram Bot API** — sending reports to Telegram

---

## 🚀 How to Run Tests Manually

1. Install dependencies:

```bash
mvn clean install
```

2. Run the tests:

```bash
mvn test
```

3. Generate and open the Allure report:

```bash
allure serve target/allure-results
```

---

## 🧰 Jenkins CI/CD Integration

Tests are automatically triggered via Jenkins:

- ✅ Connected to the GitHub repository
- ✅ Scheduled to run every hour using a trigger
- ✅ Allure reports are saved and displayed in the Jenkins UI
- ✅ Test status notifications (success/failure) are sent to Telegram

---

## 📊 Allure Reports

- Automatically generated after each test run
- Display test results for each case
- Allow you to view run history and step-by-step details

### 📸 Example Allure report:

![Allure report example](screenshots/Screenshot%20(44).png)


## 📬 Telegram Integration

After each run:

- A Telegram chat receives:
  - Test status ✅/❌
  - Execution time
  - A brief log (last 20 lines from the latest log file)

Telegram bot settings are located in `TelegramBot.java`.

### 📸 Example Telegram notification:

![Telegram report example](screenshots/Screenshot%20(45).png)

### 📸 Example Telegram notification (test failed):

![Telegram report example](screenshots/Screenshot%20(46).png)

---

## 📌 Example Tests

- Verifying placeholders on the payment form
- Checking the correctness of entered data inside the payment iframe

---

## 📋 Author

- **Vitali Molotok**  
  [GitHub: @VitaliiMalatok](https://github.com/VitaliiMalatok)

---

