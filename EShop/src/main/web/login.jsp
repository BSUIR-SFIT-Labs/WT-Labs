<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">

<body>


<section class="contact section-padding-half">
    <div class="container">
        <div class="row">
            <c:if test="${sessionScope.userId == null}">
                <div class="col-lg-8 mx-auto col-md-10 col-12 text-center">
                    <div class="col-lg-6 mx-auto col-md-7 col-12 pt-0 mt-0 text-center" data-aos="fade-up">
                        <h3 class="mb-4">Type your <strong>username</strong> and <strong>password</strong> to <strong>Log In</strong>:</h3>
                    </div>
                    <c:if test="${errmsg != null}">
                        <h5 class="mb-4 mx-auto" data-aos="fade-up">${errmsg}</h5>
                    </c:if>
                    <form action="login" method="post" class="contact-form" data-aos="fade-up" data-aos-delay="100" role="form">
                        <div class="col-lg-6 col-12 mx-auto">
                            <input required type="text" class="form-control" name="username" placeholder="Username">
                            <input required type="password" class="form-control" name="pass" placeholder="Your password">
                        </div>
                        <div class="col-lg-5 mx-auto col-7">
                            <button type="submit" class="form-control" id="submit-button" name="submit">Log In</button>
                        </div>
                    </form>
                </div>
            </c:if>
            <c:if test="${sessionScope.userId != null}">
                <c:redirect url="/"/>
            </c:if>
        </div>
    </div>
</section>


</body>
</html>