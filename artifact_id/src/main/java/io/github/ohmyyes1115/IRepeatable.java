package io.github.ohmyyes1115;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface IRepeatable {

    interface Listener {
        void onSubmitted();
        // void onFinished();
    }

    void registerListener(Listener listener);

    boolean play();

    boolean verify();

    String getTitle();

    RID getRid();

    ILevel getILevel();
    
    void setLastRepeatTime(LocalDateTime last_repeat_time);

    LocalDateTime getLastRepeatTime();

    QD getQD();

    // RSD: repeatition spending duration. i.e., how long to finish this repeatable
    void setRSD(RSD rsd);
    // RSD: repeatition spending duration. i.e., how long to finish this repeatable
    RSD getRSD();

    // void OnRepeated_Correct(LocalDateTime date_time);
    // void OnRepeated_Wrong(LocalDateTime date_time);
}

// interface IPlayable {
//     boolean play();
// }

interface IResultListener {
    void onCorrect();
    void onWrong();
}

// class Repeatable {

//     private IPlayable m_playerable = null;
//     private IResultListener m_listener = null;

//     public Repeatable(@Nonnull IPlayable playable) {
//         m_playerable = playable;
//     }

//     public boolean play(@Nonnull IResultListener listener) {
//         return m_playerable.play();
//     }

//     public void OnRepeated_Correct(LocalDateTime date_time) {
//         m_listener.onCorrect();
//     }

//     public void OnRepeated_Wrong(LocalDateTime date_time) {
//         m_listener.onWrong();
//     }

// }

// class Playable_Leetcode implements IPlayable {

//     private Leetcode_Problem m_problem;
//     private CodingView m_coding_view;

//     public Playable_Leetcode(@Nonnull Leetcode_Problem problem, CodingView codingView) {
//         m_problem = problem;
//         m_coding_view = codingView;
//     }

//     @Override
//     public boolean play() {
//         m_coding_view.setDescription(m_problem.getFilePath());
//         return true;
//     }
    
// }

interface ILeetcodeSession {

    ILoggedInProblem problem(ProblemId prob_id);

    // void gotoProblem(ProblemId prob_id);

}

abstract class LeetcodeSession_X_AbstractSelenium implements ILeetcodeSession {

    // private Token m_token;

    private LeetcodeWeb_X_Selenium m_leetcode_web;
    private WebDriver m_driver;

    public LeetcodeSession_X_AbstractSelenium(/*Token token, */LeetcodeWeb_X_Selenium web, WebDriver driver) {
        m_leetcode_web = web;
        m_driver = driver;
    }

    void gotoProblem(ProblemId prob_id) {
        ProblemLink link = m_leetcode_web.problemHelper().fromId_ToLink(prob_id);
        m_driver.get(link.toString());
    }

    // @Override // ILeetcodeSession
    // public final void gotoProblem(ProblemId prob_id) {
    //     ProblemLink link = m_leetcode_web.problemHelper().fromId_ToLink(prob_id);
    //     m_driver.get(link.toString());
    // }

    protected WebDriver getDriver() {
        return m_driver;
    }

    // protected Token getToken() {
    //     return m_token;
    // }

}

class LeetcodeSession_Selenium extends LeetcodeSession_X_AbstractSelenium {

    private LeetcodeWeb_X_Selenium m_leetcode_web;

    public LeetcodeSession_Selenium(/*Token token, */WebDriver driver, LeetcodeWeb_X_Selenium leetcode_web) {
        super(/*token, */leetcode_web, driver);
        m_leetcode_web = leetcode_web;
    }

    @Override // ILeetcodeSession
    public ILoggedInProblem problem(ProblemId prob_id) {
        return new LoggedInProblem_X_Selenium(m_leetcode_web, this, prob_id/*, getDriver()*/);
    }

}

enum SupportedLoginType {
    _3rdParty_FB,
}

interface ILeetcodeWeb {

    ILeetcodeSession logIn(SupportedLoginType loginType, String account, String password);

    Problem_NonLoggedIn problem(ProblemId prob_id);

    // ILoggedInProblem problem(Token token, ProblemId prob_id) throws TokenNotFoundException;

    void getProblemLinkFromId();
}

/**
 * This class encapsulates all available operations on the leetcode website, using web-crawler (Selenium).
 * Therefore all the clients should not access Selenium specific APIs directly.
 */
class LeetcodeWeb_X_Selenium implements ILeetcodeWeb {

    static {
        System.setProperty("webdriver.chrome.driver", ".\\artifact_id\\tools\\chromedriver.exe");
    }

    private WebDriver m_default_driver;
    
    // Performance isn't required. Use non-hashed map for now.
    // private Map<Token, WebDriver> m_drivers = new TreeMap<>();
    
    private ProblemHelper m_prob_helper;

    public LeetcodeWeb_X_Selenium() {
        m_default_driver = createWebDriver();
        m_prob_helper = new ProblemHelper(m_default_driver);
    }

    @Override  // ILeetcodeWeb
    public ILeetcodeSession logIn(SupportedLoginType loginType, String account, String password) {
        
        if (loginType == SupportedLoginType._3rdParty_FB) {
    
            String url_loginWithFB = "https://leetcode.com/accounts/facebook/login/?next=%2F";
              
            WebDriver driver = createWebDriver();

            // get()開啟一個站點
            driver.get(url_loginWithFB);
    
            WebDriverWait wait = new WebDriverWait(driver, 10,000);

            driver.findElement(By.cssSelector("input[type='text'][class='inputtext _55r1 inputtext _1kbt inputtext _1kbt']")).sendKeys(account);
            driver.findElement(By.cssSelector("input[type='password'][class='inputtext _55r1 inputtext _1kbt inputtext _1kbt']")).sendKeys(password);
            driver.findElement(By.cssSelector("button[id='loginbutton']")).click();
    
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("button[id='loginbutton']")));  // login-button gone

            // Token token = new Token() {};
            // m_drivers.put(token, driver);

            return new LeetcodeSession_Selenium(driver, this);
        }

        throw new UnsupportedOperationException("Supported_3rdparty_Login unsupported: " + loginType);
    }

    public ProblemHelper problemHelper() {
        return m_prob_helper;
    }

    @Override  // ILeetcodeWeb
    public void getProblemLinkFromId() {
    }

    // void gotoProblem(ProblemId prob_id) {
    //     // 1. Find the link from m_probLink_cache
    //     //    or find the link from id, and save the link to m_probLink_cache

    //     if (!m_probLink_cache.containsKey(prob_id)) {
    //         m_probLink_cache.put(prob_id, GetProblemLink.fromId(prob_id, m_drivers));
    //     }
        
    //     // https://leetcode.com/problemset/all/

    //     // 3. go to link
    // }

    @Override  // ILeetcodeWeb
    public Problem_NonLoggedIn problem(ProblemId prob_id) {
        return new Problem_NonLoggedIn(this, prob_id);
    }

    // @Override  // ILeetcodeWeb
    // public ILoggedInProblem problem(Token token, ProblemId prob_id) throws TokenNotFoundException {
    //     if (!m_drivers.containsKey(token)) {
    //         throw new TokenNotFoundException();
    //     }
        
    //     return new LoggedInProblem_Selenium(this, prob_id, token);
    // }

    private WebDriver createWebDriver() {
    
        // 初始化一個chrome瀏覽器例項，例項名稱叫driver
        WebDriver driver = new ChromeDriver();
        // 最大化視窗
        driver.manage().window().maximize();
        // 設定隱性等待時間
        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

        return driver;
    }
}

class ProblemHelper {

    private FromId_ToLink_Cache m_idToLink_cache;

    public ProblemHelper(WebDriver driver) {
        m_idToLink_cache = new FromId_ToLink_Cache(driver);
    }

    public ProblemLink fromId_ToLink(ProblemId prob_id) {
        return m_idToLink_cache.get(prob_id);
    }
    
}

class FromId_ToLink_Cache {

    private WebDriver m_driver;

    // Performance isn't required. Use non-hashed map for now.
    private Map<ProblemId, ProblemLink> m_probLink_cache = new TreeMap<>();

    public FromId_ToLink_Cache(WebDriver driver) {
        m_driver = driver;
    }

    public ProblemLink get(ProblemId prob_id) {
        if (!m_probLink_cache.containsKey(prob_id)) {
            
            m_driver.get("https://leetcode.com/problemset/all/?search=" + prob_id);

            WebDriverWait wait = new WebDriverWait(m_driver, 10,000);

            WebElement prob_element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, 'problems') and contains(text(), '" + prob_id + "')]")));
            ProblemLink prob_link = ProblemLink.of(prob_element.getAttribute("href"));
            m_probLink_cache.put(prob_id, prob_link);
        }

        return m_probLink_cache.get(prob_id);
    }
}

class GetProblemLink {

    public static ProblemLink fromId(ProblemId prob_id) {
        return null;
    }
}

class Link {

    private String m_url;

    public static Link of(String url) {
        return new Link(url);
    }

    @Override
    public String toString() {
        return m_url;
    }

    public String getUrl() {
        return m_url;
    }

    private Link(String url) {
        m_url = url;
    }
}

class ProblemLink {

    private Link m_link;

    public static ProblemLink of(String url) {
        return new ProblemLink(Link.of(url));
    }

    @Override
    public String toString() {
        return m_link.toString();
    }

    public String getUrl() {
        return toString();
    }

    private ProblemLink(Link link) {
        m_link = link;
    }
}

// interface Token {}

class Problem_NonLoggedIn {

    private LeetcodeWeb_X_Selenium m_leetcode_web;
    private ProblemId m_prob_id;

    public Problem_NonLoggedIn(LeetcodeWeb_X_Selenium leetcode_web, ProblemId prob_id) {
        m_leetcode_web = leetcode_web;
        m_prob_id = prob_id;
    }
}

// namespace: Selenium
interface ILoggedInProblem {

    interface OnSubmittedResultListener {

        void OnCompileError(String err_msg);
        void OnTimeLimitExceeded(String last_executed_input);
        void OnWrongAnswer(String last_executed_input);
        void OnAccepted();
    }

    void runCode(String submission, OnSubmittedResultListener listener);
}

// namespace: Selenium
class LoggedInProblem_X_Selenium implements ILoggedInProblem {

    private LeetcodeWeb_X_Selenium m_leetcode_web;
    private ProblemId m_prob_id;
    // private WebDriver m_driver;
    private LeetcodeSession_X_AbstractSelenium m_leetcode_session;

    public LoggedInProblem_X_Selenium(LeetcodeWeb_X_Selenium web, LeetcodeSession_X_AbstractSelenium session, ProblemId prob_id/*, WebDriver driver*/) {
        m_leetcode_web = web;
        m_prob_id = prob_id;
        // m_driver = driver;
        m_leetcode_session = session;
    }

    @Override  // ILoggedInProblem
    public void runCode(String submission, OnSubmittedResultListener listener) {
        m_leetcode_session.gotoProblem(m_prob_id);
        // m_leetcode_session.runCode(submission);
        // run ...
    }

    ProblemId getProblemId() {
        return m_prob_id;
    }
}

class TokenNotFoundException extends Exception {}

class ProblemId {

    int m_prob_id;

    public static ProblemId of(int prob_id) {
        return new ProblemId(prob_id);
    }

    // change to protected
    public int getId() {
        return m_prob_id;
    }

    @Override
    public String toString() {
        return String.valueOf(m_prob_id);
    }

    private ProblemId(int prob_id) {
        m_prob_id = prob_id;
    }
}

class LeetcodeManager {

    public static LeetcodeManager create(SupportedLoginType loginType, String account, String password) {
        // return LeetcodeManager(driver);
        return null;
    }

    public LeetcodeManager(String fbA) {

    }
}

interface ILeetcodeVerifier {
    boolean verify(String submission);
}

class LeetcodeVerifier_X_OnlineJudge implements ILeetcodeVerifier {

    public LeetcodeVerifier_X_OnlineJudge() {

    }

    @Override
    public boolean verify(String submission) {
        return false;
    }
    
}

class LeetcodeVerifierFactory {

    private LeetcodeManager m_LeetcodeManager;

    public LeetcodeVerifierFactory() {
        m_LeetcodeManager = LeetcodeManager.create(SupportedLoginType._3rdParty_FB, "oh.my.yes@hotmail.com", "AAAAaaaa1115");
    }

    public ILeetcodeVerifier create(int prob_id) {
        return null;
    }
}

// interface IRepeatableFactory {
//     IRepeatable createRepeatable();
// }

// class RepeatableFactory_X_Leetcode implements IRepeatableFactory {
//     private Repeatable_Leetcode_VO m_problem;
//     private CodingView m_leetcode_view;
    
//     public RepeatableFactory_X_Leetcode(Repeatable_Leetcode_VO problem, CodingView leetcode_view) {
//         m_problem = problem;
//         m_leetcode_view = leetcode_view;
//     }

//     public IRepeatable createRepeatable() {
//         LeetcodeVerifier verifier = new LeetcodeVerifier(m_problem);
//         m_leetcode_view.registerListener(verifier);

//         return new Repeatable(
//                 new Playable_Leetcode(m_problem, m_leetcode_view),
//                 verifier);
//     }
// }
