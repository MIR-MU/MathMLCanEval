

        <div class="container">

            <form class="form-signin" role="form" action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
                <h2 class="form-signin-heading">Please sign in</h2>
                <input type="text" name="j_username" class="form-control" placeholder="Email address" required autofocus>
                <input type="password" name="j_password" class="form-control" placeholder="Password" required>
                <label class="checkbox">
                    <input type="checkbox" value="remember-me"> Remember me
                </label>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            </form>

        </div> <!-- /container -->
