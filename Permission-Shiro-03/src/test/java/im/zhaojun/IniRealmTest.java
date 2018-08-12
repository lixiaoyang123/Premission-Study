package im.zhaojun;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.util.Arrays;

/**
 * Shiro IniRealm 测试
 */
public class IniRealmTest {

    @Test
    public void testIniRealm() {
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        Realm iniRealm = new IniRealm("classpath:shiro.ini");
        defaultSecurityManager.setRealm(iniRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhao", "123456");

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("登陆失败");
        }

        System.out.println("--------------------认证--------------------");
        System.out.println("是否具备 admin 权限: " + subject.hasRole("admin"));
        System.out.println("是否具备 user 权限: " + subject.hasRole("user"));
        System.out.println("是否同时具备 admin 和 user 权限: " + subject.hasAllRoles(Arrays.asList("admin", "user")));

        System.out.println("--------------------授权--------------------");
        System.out.println("是否具备 user:delete 权限" + subject.isPermitted("user:delete"));
        System.out.println("是否具备 user:select 权限" + subject.isPermitted("user:select"));
        System.out.println("是否同时具备 user:delete 和 user:select 权限" + subject.isPermittedAll("user:delete", "user:select"));
    }
}
