package tri.dev;

import tri.dev.data.dao.DatabaseDao;
import tri.dev.data.dao.ProductDao;
import tri.dev.data.model.Product;
import tri.dev.util.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import tri.dev.data.dao.CategoryDao;
import tri.dev.data.model.Category;

public class ShopServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDao productDao = DatabaseDao.getInstance().getProductDao();

        // Lấy tổng số sản phẩm
        int totalProducts = productDao.countAll();
        int page = 1;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                // Giữ mặc định là 1 nếu tham số không hợp lệ
            }
        }

        // Lọc sản phẩm
        String property = request.getParameter("property");
        String order = request.getParameter("order");

        List<Product> productList = new ArrayList<>(); // Khởi tạo productList

        if (property != null && order != null) {
            // Kiểm tra nếu giá trị property và order là hợp lệ
            if (property.equals("name") || property.equals("price") || property.equals("createdAt")) {
                if (order.equals("asc") || order.equals("desc")) {
                    productList = productDao.filterProducts(property, order, (page - 1) * Constants.PER_PAGE, Constants.PER_PAGE);
                } else {
                    // Giá trị order không hợp lệ, có thể xử lý ở đây
                    productList = productDao.getProducts((page - 1) * Constants.PER_PAGE, Constants.PER_PAGE); // fallback
                }
            } else {
                // Giá trị property không hợp lệ, có thể xử lý ở đây
                productList = productDao.getProducts((page - 1) * Constants.PER_PAGE, Constants.PER_PAGE); // fallback
            }
        } else {
            productList = productDao.getProducts((page - 1) * Constants.PER_PAGE, Constants.PER_PAGE);
        }

        int numberPage = (int) Math.ceil((double) totalProducts / Constants.PER_PAGE);

        // Đặt các thuộc tính cần thiết để hiển thị trên giao diện
        request.setAttribute("page", page);
        request.setAttribute("totalProducts", totalProducts);
        request.setAttribute("numberPage", numberPage);
        request.setAttribute("productList", productList);

        // Truy vấn danh sách danh mục từ database
        CategoryDao categoryDao = DatabaseDao.getInstance().getCategoryDao();
        List<Category> categoryList = categoryDao.findAll();
        request.setAttribute("categoryList", categoryList);

        // Forward đến trang shop.jsp
        request.getRequestDispatcher("shop.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
