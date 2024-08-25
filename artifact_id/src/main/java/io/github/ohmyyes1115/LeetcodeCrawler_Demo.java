package io.github.ohmyyes1115;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class LeetcodeCrawler_Demo {

    public static void butFailedToEditCodingArea() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", ".\\artifact_id\\tools\\chromedriver.exe");  

        String url_loginWithFB = "https://leetcode.com/accounts/facebook/login/?next=%2F";

        //初始化一個chrome瀏覽器例項，例項名稱叫driver  
        WebDriver driver = new ChromeDriver();
        //最大化視窗  
        driver.manage().window().maximize();
        //設定隱性等待時間  
        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

        WebDriverWait wait = new WebDriverWait(driver, 10,000);
        
        if (false) {
            // get()開啟一個站點  
            driver.get(url_loginWithFB);

            driver.findElement(By.cssSelector("input[type='text'][class='inputtext _55r1 inputtext _1kbt inputtext _1kbt']")).sendKeys("oh.my.yes@hotmail.com");
            driver.findElement(By.cssSelector("input[type='password'][class='inputtext _55r1 inputtext _1kbt inputtext _1kbt']")).sendKeys("AAAAaaaa1115");
            driver.findElement(By.cssSelector("button[id='loginbutton']")).click();

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("button[id='loginbutton']")));  // login-button gone
        }

        // demoSubmit(driver, wait);
        // demoRunCode(driver, wait);
        // demo_GetProblemLinkFromId(driver, wait);
        demoEditCode_BUT_FAILED(driver, wait);
          
        Thread.sleep(2000000);

        //關閉並退出瀏覽器  
        driver.quit();
    }

    private static void demo_GetProblemLinkFromId(WebDriver driver, WebDriverWait wait) {
        String prob_id = "100";

        // driver.get("https://leetcode.com/problemset/all/");
        
        // text-field: Search Question
        // driver.findElement(By.cssSelector("input[placeholder='Search questions']")).sendKeys(prob_id);

        driver.get("https://leetcode.com/problemset/all/?search=" + prob_id);

        WebElement prob_element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, 'problems') and contains(text(), '" + prob_id + "')]")));
        String prob_link = prob_element.getAttribute("href");
        
        System.out.println(prob_link);
    }

    private static void demoSubmit(WebDriver driver, WebDriverWait wait) throws InterruptedException  {
        driver.get("https://leetcode.com/problems/two-sum/");

        // Submit button
        WebElement btn_submit = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-cy='submit-code-btn']")));
        Thread.sleep(10000);
        btn_submit.click();

        // xpath ref: https://zh.wikipedia.org/zh-tw/XPath
        
        WebElement title_submitRes = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='error__10k9' or @class='error__2Ft1' or @class='success__3Ai7']")));  // 'Compile Error' 'Wrong Answer' || 'Success'

        String titleStr_submitRes = title_submitRes.getText().toLowerCase();

        // CE
        if (titleStr_submitRes.contains("compile") && titleStr_submitRes.contains("error")) {
            System.out.println(title_submitRes.getText());
            Thread.sleep(1000);
            System.out.println(driver.findElement(By.xpath("//div[@class='css-9m2dvj-ContentWrapper e5i1odf1']//div[2]")).getText());
        }
        // TLE
        else if (titleStr_submitRes.contains("time") && titleStr_submitRes.contains("limit") && titleStr_submitRes.contains("exceeded")) {
            System.out.println(title_submitRes.getText());

            Thread.sleep(1000);
                
            WebElement last_executed_input = driver.findElement(
                By.xpath("//div[@class='css-1ubm0bb-Value e5i1odf2']"));
            System.out.println("Last executed input: " + last_executed_input.getText());
        }
        // WA
        else if (titleStr_submitRes.contains("wrong") && titleStr_submitRes.contains("answer")) {
            System.out.println(title_submitRes.getText());

            Thread.sleep(1000);
                
            WebElement your_input = driver.findElement(
                By.xpath("//div[@class='container__nthg']/div[2]//div[@class='css-1ubm0bb-Value e5i1odf2']"));
            System.out.println(your_input.getText());
                
            WebElement output = driver.findElement(
                By.xpath("//div[@class='container__nthg']/div[3]//div[@class='css-1ubm0bb-Value e5i1odf2']"));
            System.out.println(output.getText());
                
            WebElement expected = driver.findElement(
                By.xpath("//div[@class='container__nthg']/div[4]//div[@class='css-1ubm0bb-Value e5i1odf2']"));
            System.out.println(expected.getText());
        }
        // AC
        else if (titleStr_submitRes.contains("success")) {
            System.out.println(title_submitRes.getText());
        }
    }

    private static void demoEditCode_BUT_FAILED(WebDriver driver, WebDriverWait wait) throws InterruptedException {
        driver.get("https://leetcode.com/problems/same-tree/");

        // 'Run Code' button
        // WebElement coding_area = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='cm-variable' and text()='isSameTree']")));  // Element not interactable
        List<WebElement> coding_area = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//pre[@class=' CodeMirror-line ']")));
        Thread.sleep(2000);
        coding_area.forEach(e ->  System.out.println(e.getText()));

        // <div style="position: relative;" class="CodeMirror-activeline">
        //    <div class="CodeMirror-activeline-background CodeMirror-linebackground"></div>
        //    <div class="CodeMirror-gutter-background CodeMirror-activeline-gutter" style="left: -56px; width: 56px;"></div>
        //    <div class="CodeMirror-gutter-wrapper CodeMirror-activeline-gutter" style="left: -56px;">
        //       <div class="CodeMirror-linenumber CodeMirror-gutter-elt" style="left: 0px; width: 32px;">16</div>
        //    </div>
        //
        //    <pre class=" CodeMirror-line " role="presentation">
        //       <span role="presentation" style="padding-right: 0.1px;">
        //          &nbsp; &nbsp; &nbsp; &nbsp;
        //          <span class="cm-operator">
        //             &amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;
        //          </span>
        //       </span>
        //    </pre>
        // </div>
        String code =
        "code_div = document.querySelector('body');"
        // "code_div = document.querySelector('div.CodeMirror-code');"
         + "div = document.createElement('div');"
         + "pre = document.createElement('pre');"
         + "sp = document.createElement('span');"
         + "text = document.createTextNode('Hello');"
         + "sp.appendChild(text);"
         + "pre.appendChild(sp);"
         + "div.appendChild(pre);"
         + "code_div.appendChild(div);"
        ;

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(code);
        System.out.println("executeScript done");

        List<WebElement> divs = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[contains(text(), 'Hello')]")));
        System.out.println("div.size = " + divs.size());

        Thread.sleep(100000);
    }

    private static void demoRunCode(WebDriver driver, WebDriverWait wait) throws InterruptedException {

        // 'Run Code' button
        WebElement btn_runCode = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-cy='run-code-btn']")));
        Thread.sleep(10000);
        btn_runCode.click();

        // xpath ref: https://zh.wikipedia.org/zh-tw/XPath
        
        WebElement title_runCodeRes = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[" +
                            "text()='Compile Error' or " +
                            "text()='Time Limit Exceeded' and @class='error__10k9' or " +
                            "text()='Wrong Answer' or " +
                            "text()='Accepted' and @class='finished__Zi5m'" +
                        "]")));

        String titleStr_runCodeRes = title_runCodeRes.getText().toLowerCase();

        // CE
        if (titleStr_runCodeRes.contains("compile") && titleStr_runCodeRes.contains("error")) {
            System.out.println(title_runCodeRes.getText());
            System.out.println(driver.findElement(By.cssSelector("div[class='css-2plaxd-Error e5i1odf4']")).getText());
        }
        // TLE
        else if (titleStr_runCodeRes.contains("time") && titleStr_runCodeRes.contains("limit") && titleStr_runCodeRes.contains("exceeded")) {
            System.out.println(title_runCodeRes.getText());

            Thread.sleep(1000);

            // The 'Testcase' content will be generated only we choose 'Testcase' tab first.
            // List<WebElement> testcase = driver.findElements(
            //     By.xpath("//div[@class='ace_layer ace_text-layer']/div[@class='ace_line_group']"));
            
            // testcase.forEach(line -> System.out.println(line.getText()));
        }
        // WA
        else if (titleStr_runCodeRes.contains("wrong") && titleStr_runCodeRes.contains("answer")) {
            System.out.println(title_runCodeRes.getText());

            Thread.sleep(1000);
                
            WebElement your_input = driver.findElement(
                By.xpath("//div[@data-cy='run-code-finished']/div[2]"));
            System.out.println(your_input.getText());
                
            WebElement output = driver.findElement(
                By.xpath("//div[@data-cy='run-code-finished']/div[3]/div/div"));
            System.out.println(output.getText());
                
            WebElement expected = driver.findElement(
                By.xpath("//div[@data-cy='run-code-finished']/div[4]"));
            System.out.println(expected.getText());
        }
        // AC
        else if (titleStr_runCodeRes.contains("accepted")) {
            System.out.println(title_runCodeRes.getText());

            Thread.sleep(1000);
                
            WebElement your_input = driver.findElement(
                By.xpath("//div[@data-cy='run-code-finished']/div[2]"));
            System.out.println(your_input.getText());
                
            WebElement output = driver.findElement(
                By.xpath("//div[@data-cy='run-code-finished']/div[3]/div/div"));
            System.out.println(output.getText());
                
            WebElement expected = driver.findElement(
                By.xpath("//div[@data-cy='run-code-finished']/div[4]"));
            System.out.println(expected.getText());
        }
    }
}