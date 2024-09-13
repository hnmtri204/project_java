<%@include file="./inc/header.jsp" %>
<section id="page-header">
    <h2>#${keyword}</h2>
    <p>Save more with coupons & up to 70% off!</p>
    <div class="wrap-form">
        <div class="search-form">
            <form action="SearchServlet" class="w-full" method="get">
                <div class="from-control flex items-center relative w-full">
                    <svg xmlns="http://www.w3.org/2000/svg" class="icon-search" fill="none" viewBox="0 0 24 24"
                         stroke="currentColor" stroke-width="2">
                        <path stroke-linecap="round" stroke-linejoin="round"
                              d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
                    </svg>
                    <input name="keyword" placeholder="Search ..." type="text"
                           class="search-input w-full bg-white border-[1px] rounded-[30px] h-[40px] border-[#b3b3b3] pl-[40px]">
                </div>
            </form>
        </div>
    </div>
</section>

<section id="product1" class="section-p1">
    <div class="prod-cont">
        <c:forEach items="${productList}" var="product">
            <div class="prod" onclick="window.location.href = 'sproduct.html';">
                <img src="${product.thumbnail}" alt="">
                    <div class="des">
                        <span>adidas</span>
                        <h5>${product.name}</h5>
                        <div class="star">
                            <i class="fas fa-star"></i>
                            <i class="fas fa-star"></i>
                            <i class="fas fa-star"></i>
                            <i class="fas fa-star"></i>
                            <i class="fas fa-star"></i>
                        </div>
                        <h4>$${product.price}</h4>
                    </div>
                    <a href="#"><i class="fa-solid fa-cart-shopping cart"></i></a>
            </div>
        </c:forEach>
</section>

<section id="pagination" class="section-p1">
    <a href="#">1</a>
    <a href="#">2</a>
    <a href="#"><i class="fa-solid fa-arrow-right"></i></a>
</section>
<%@include file="./inc/footer.jsp" %>
