package com.hspedu.furns.web;

import com.hspedu.furns.entity.Furn;
import com.hspedu.furns.entity.Page;
import com.hspedu.furns.service.FurnService;
import com.hspedu.furns.service.impl.FurnServiceImpl;
import com.hspedu.furns.utils.DataUtils;
import com.hspedu.furns.utils.WebUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @author 韩顺平
 * @version 1.0
 */
public class FurnServlet extends BasicServlet {

    //定义一个属性 FurnSerivce
    //老韩小技巧： 如何设置idea 的标签页
    private FurnService furnService = new FurnServiceImpl();


    /**
     * 处理分页请求
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int pageNo = DataUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = DataUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        //调用service方法, 获取Page对象
        Page<Furn> page = furnService.page(pageNo, pageSize);
        //将page放入到request域
        req.setAttribute("page", page);
        //请求转发到furn_manage.jsp
        req.getRequestDispatcher("/views/manage/furn_manage.jsp")
                .forward(req, resp);
    }


    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //将提交修改的家居信息，封装成Furn对象

        //如果你的表单是enctype="multipart/form-data", req.getParameter("id") 得不到id
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        //获取到对应furn对象[从db中获取]
        Furn furn = furnService.queryFurnById(id);
        //todo 做一个判断 furn为空就不处理

        //1. 判断是不是文件表单(enctype="multipart/form-data")
        if (ServletFileUpload.isMultipartContent(req)) {
            //2. 创建 DiskFileItemFactory 对象, 用于构建一个解析上传数据的工具对象
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            //3. 创建一个解析上传数据的工具对象

            ServletFileUpload servletFileUpload =
                    new ServletFileUpload(diskFileItemFactory);
            //解决接收到文件名是中文乱码问题
            servletFileUpload.setHeaderEncoding("utf-8");

            //4. 关键的地方, servletFileUpload 对象可以把表单提交的数据text / 文件
            //   将其封装到 FileItem 文件项中
            try {
                List<FileItem> list = servletFileUpload.parseRequest(req);
                //遍历，并分别处理=> 自然思路
                for (FileItem fileItem : list) {
                    //判断是不是一个文件=> 你是OOP程序员
                    if (fileItem.isFormField()) {//如果是true就是文本 input text(普通的表单字段)

                        if ("name".equals(fileItem.getFieldName())) {//家居名
                            furn.setName(fileItem.getString("utf-8"));
                        } else if ("maker".equals(fileItem.getFieldName())) {//制造商
                            furn.setMaker(fileItem.getString("utf-8"));
                        } else if ("price".equals(fileItem.getFieldName())) {//价格
                            furn.setPrice(new BigDecimal(fileItem.getString()));
                        } else if ("sales".equals(fileItem.getFieldName())) {//销量
                            furn.setSales(new Integer(fileItem.getString()));
                        } else if ("stock".equals(fileItem.getFieldName())) {//库存
                            furn.setStock(new Integer(fileItem.getString()));
                        }

                    } else {//是一个文件

                        //获取上传的文件的名字
                        String name = fileItem.getName();

                        //如果用户没有选择新的图片, name = ""
                        if (!"".equals(name)) {
                            //1.指定一个目录 , 就是我们网站工作目录下
                            String filePath = "/" + WebUtils.FURN_IMG_DIRECTORY;
                            //2. 获取到完整目录 [io/servlet基础]
                            String fileRealPath =
                                    req.getServletContext().getRealPath(filePath);

                            //3. 创建这个上传的目录=> 创建目录?=> Java基础
                            File fileRealPathDirectory = new File(fileRealPath);
                            if (!fileRealPathDirectory.exists()) {//不存在，就创建
                                fileRealPathDirectory.mkdirs();//创建
                            }

                            //4. 将文件拷贝到fileRealPathDirectory目录
                            //   构建一个上传文件的完整路径 ：目录+文件名
                            //   对上传的文件名进行处理, 前面增加一个前缀，保证是唯一即可, 不错
                            name = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + "_" + name;
                            String fileFullPath = fileRealPathDirectory + "/" + name;
                            fileItem.write(new File(fileFullPath)); //保存

                            fileItem.getOutputStream().close();//关闭流

                            //更新家居的图片路径
                            furn.setImgPath(WebUtils.FURN_IMG_DIRECTORY + "/" + name);
                            //todo 删除原来旧的不用的图片
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("不是文件表单...");
        }

        //更新furn对象->DB
        furnService.updateFurn(furn);
        //可以请求转发到更新成功的页面..
        req.getRequestDispatcher("/views/manage/update_ok.jsp").forward(req, resp);

    }


    //protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //    //将提交修改的家居信息，封装成Furn对象
    //
    //    Furn furn = DataUtils.copyParamToBean(req.getParameterMap(), new Furn());
    //    furnService.updateFurn(furn);//没有修改img_path
    //    //重定向到家居列表页, 看到最新数据
    //    //resp.sendRedirect(req.getContextPath()
    //    //        + "/manage/furnServlet?action=list");
    //    //这里我们考虑分页,并带上pageNo, 老师理解: 这里同学比较吃力.
    //    resp.sendRedirect(req.getContextPath()
    //            + "/manage/furnServlet?action=page&pageNo=" + req.getParameter("pageNo"));
    //
    //}

    /**
     * 处理回显家居信息的请求
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void showFurn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        Furn furn = furnService.queryFurnById(id);
        //将furn放入到request域中
        req.setAttribute("furn", furn);
        //老师说明: 如果请求带来的参数 pageNo=1 , 而且是请求转发到下一个页面, 在下一个页面可以通过 param.pageNo
        //System.out.println("furn=>" + furn);
        //请求转发到
        req.getRequestDispatcher("/views/manage/furn_update.jsp")
                .forward(req, resp);
    }

    protected void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //老韩解读
        //为了防止接收的id 不是一个数字的字符串 比如"hello"
        //我就使用一个工具方法, 如果是可以转成数字的字符串，就转，否则就返回一个默认值
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        furnService.deleteFurnById(id);
        //重定向到家居列表页, 看到最新数据
        //resp.sendRedirect(req.getContextPath()
        //        + "/manage/furnServlet?action=list");
        resp.sendRedirect(req.getContextPath()
                + "/manage/furnServlet?action=page&pageNo="
                + req.getParameter("pageNo"));

    }

    /**
     * 处理添加家居的请求
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取家居信息
        //String name = req.getParameter("name");
        //String maker = req.getParameter("maker");
        //String price = req.getParameter("price");
        //String sales = req.getParameter("sales");

        //我们可以对获取的到数据, 进行一个校验
        //1. 使用java的正则表达式来验证 sales是一个正整数
        //2. 如果没有通过校验，则直接返回furn_add.jsp -> request.setAttribute("mes","xx")
        //3. 这里可以直接进行转换
        //try {
        //    int i = Integer.parseInt(sales);
        //}catch (NumberFormatException e) {
        //    //System.out.println("转换异常...");
        //    req.setAttribute("mes", "销量数据格式不对...");
        //    //返回到furn_add.jsp
        //    req.getRequestDispatcher("/views/manage/furn_add.jsp")
        //            .forward(req, resp);
        //    return;
        //}

        //String stock = req.getParameter("stock");
        //图片的路径 imgPath 使用默认即可


        //Furn furn = null;
        //try {
        //    furn = new Furn(null, name, maker, new BigDecimal(price),
        //            new Integer(sales), new Integer(stock), "assets/images/product-image/default.jpg");
        //} catch (NumberFormatException e) {
        //    req.setAttribute("mes", "添加数据格式不对...");
        //    //返回到furn_add.jsp
        //    req.getRequestDispatcher("/views/manage/furn_add.jsp")
        //            .forward(req, resp);
        //    return;
        //}

        //后面我们会学习SpringMVC -> 专门的用于数据校验的规则/框架 JSR303... Hibernate Validator

        //Furn furn = new Furn(null, name, maker, new BigDecimal(price),
        //        new Integer(sales), new Integer(stock), "assets/images/product-image/default.jpg");


        //这里我们使用第二种方式, 完成将前端提交的数据， 封装成Furn的Javabean对象
        //使用BeanUtils完成javabean对象的自动封装.

        //Furn furn = new Furn();
        //try {
        //    //老韩解读 讲 req.getParameterMap() 数据封装到furn 对象
        //    //使用反射将数据封装, 有一个前提就是表单提交的数据字段名
        //    //<input name="maker" style="width: 90%" type="text" value=""/>
        //    //需要和封装的Javabean的属性名一致
        //    BeanUtils.populate(furn, req.getParameterMap());
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}

        //自动将提交的数据，封装到Furn对象

        Furn furn =
                DataUtils.copyParamToBean(req.getParameterMap(), new Furn());
        furnService.addFurn(furn);
        //请求转发到家居显示页面, 即需要重新走一下 FurnServlet-list方法
        //req.getRequestDispatcher("/manage/furnServlet?action=list")
        //        .forward(req, resp);
        //老师说明: 因为这里使用请求转发, 当用户刷新页面时, 会重新发出一次添加请求
        //就会造成数据重复提交： 解决方案使用 重定向即可.
        //因为重定向实际是让浏览器重新发请求, 所以我们回送的url , 是一个完整url
        //resp.sendRedirect(req.getContextPath()
        //        + "/manage/furnServlet?action=list");
        resp.sendRedirect(req.getContextPath()
                + "/manage/furnServlet?action=page&pageNo="
                + req.getParameter("pageNo"));

    }

    /**
     * 这里我们使用前面的模板设计模式+反射+动态绑定来的调用到list方法
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("FurnServlet list方法被调...");
        List<Furn> furns = furnService.queryFurns();
        //把furns集合放入到request域
        req.setAttribute("furns", furns);
        //请求转发
        req.getRequestDispatcher("/views/manage/furn_manage.jsp")
                .forward(req, resp);
    }
}
