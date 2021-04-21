# 使用oauth 实现 GitHub 登录

## application.yml

```yml
spring:
  security:
    oauth2:
      client:
        registration:
          github:
            clientId: faca7********cd2c
            clientSecret: f3d8c28eeb9a************94c843faf3
            
          
```

## WebSecurityConfigurerAdapter

``` java
@RestController
@SpringBootApplication
public class OauthApplication extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(OauthApplication.class, args);
    }

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    // ...

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .logout(l -> l
                        .logoutSuccessUrl("/").permitAll()
                )
                .authorizeRequests(a -> a
                        .antMatchers("/", "/error", "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                ).csrf(c -> c
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        )
                .oauth2Login();
        // @formatter:on
    }


}
```

## index.html

```html
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>Demo</title>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width"/>
    <base href="/"/>
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <script type="text/javascript" src="/webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/webjars/js-cookie/js.cookie.js"></script>
</head>
<body>
<h1>Demo</h1>
<div class="container"></div>
<div class="container unauthenticated"> With GitHub: <a href="/oauth2/authorization/github">click here</a>
</div>
<div class="container authenticated" style="display:none">
    Logged in as: <span id="user"></span>
    <div>
        <button onClick="logout()" class="btn btn-primary">Logout</button>
    </div>
</div>


</body>

<script type="text/javascript">
    $.get("/user", function(data) {
        $("#user").html(data.name);
        $(".unauthenticated").hide()
        $(".authenticated").show()
    });

    var logout = function() {
        $.post("/logout", function() {
            $("#user").html('');
            $(".unauthenticated").show();
            $(".authenticated").hide();
        })
        return true;
    }

    $.ajaxSetup({
        beforeSend : function(xhr, settings) {
            if (settings.type == 'POST' || settings.type == 'PUT'
                || settings.type == 'DELETE') {
                if (!(/^http:.*/.test(settings.url) || /^https:.*/
                    .test(settings.url))) {
                    // Only send the token to relative URLs i.e. locally.
                    xhr.setRequestHeader("X-XSRF-TOKEN",
                        Cookies.get('XSRF-TOKEN'));
                }
            }
        }
    });
</script>
</html>
```









G20210675010185