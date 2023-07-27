<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1">
        <title>Insert title here</title>
        <link rel="stylesheet"
              href="templateLogin/fonts/material-icon/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" href="templateLogin/css/style.css">
        <script src="https://www.google.com/recaptcha/api.js"></script>

        <style type="text/css">
            .error {
                color: red;
            }
        </style>
    </head>
    <body>
        <div>
            <div class="signup">
                <div class="container">
                    <div class="signup-content">
                        <div class="signup-form">
                            <h2 class="form-title">Sign up</h2>
                            <form method="POST" class="register-form" action="signup"
                                  id="register-form">
                                <div class="form-group">
                                    <label for="name"><i
                                            class="zmdi zmdi-account material-icons-name"></i></label> <input
                                        type="text" onblur="isUsernameExist()" required name="user" id="name" placeholder="Account" />
                                </div>
                                <div class="error" id="errUser">${errorPassword}</div>
                                <div class="error ">${errorName}</div>
                                <div class="form-group">
                                    <label for="pass"><i class="zmdi zmdi-lock"></i></label> <input
                                        type="password" required onblur="checkRegex('pass', 'errPwd', /(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[A-Za-z\d]{6,}/g, 'Password must contains at least 1 upper, 1 lower and more than 6 letters!')" required="" name="pwd" id="pass" placeholder="Password" />
                                </div>
                                <div class="form-group">
                                    <label for="pass"><i class="zmdi zmdi-lock"></i></label> <input
                                        type="password" required="" onblur="confirmPassword()" id="repass" placeholder="Confirm password" />
                                </div>
                                <div class="error" id="errPwd">${errorPassword}</div>
                                <div class="form-group">
                                    <label for="gmail"><i class="zmdi zmdi-email"></i></label> <input
                                        type="email" name="email"  onblur="checkRegex('email', 'errEmail', /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/g, 'Email invalid!')" required id="email" placeholder="Email" />
                                </div>
                                <div class="error" id="errEmail">${errorEmail}</div>

                                <div class="g-recaptcha"
                                     data-sitekey="6Lf9yVMkAAAAAH8teVX5Z436cQalHCiKzF-J5I4S"></div> 
                                <div style="color: red;">${error }</div>
                                <div class="form-group form-button">
                                    <input type="submit" name="signup" id="signup"
                                           class="form-submit" value="Register" />
                                </div>
                            </form>
                        </div>
                        <div class="signup-image">
                            <figure>
                                <img src="templateLogin/images/signup-image.jpg"
                                     alt="sign up image">
                            </figure>
                            <a href="login.jsp" class="signup-image-link">I
                                am already member</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            
            const checkRegex = (field, err, regex, msg) => {
                document.getElementById(err).innerHTML = "";
                const pwd = document.getElementById(field).value;
                if (!pwd.match(regex)) {
                    document.getElementById(err).innerHTML = msg;
                    return false;
                }
                return true;
            };
            const confirmPassword = () => {
                document.getElementById("errPwd").innerHTML = "";
                if (document.getElementById("pass").value === document.getElementById("repass").value)
                    return true;
                document.getElementById("errPwd").innerHTML = "Password not match!";
                return false;
            };
            const isUsernameExist = async () => {
                const  username = document.getElementById("name").value;
                document.getElementById("errUser").innerHTML = "";

                const res = await(await fetch('users?username=' + username)).json();
                if (res) {
                    document.getElementById("errUser").innerHTML = "Username exists!";
                    return false;
                }
                return true;
            };
        </script>
    </body>
</html>