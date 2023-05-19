package com.shuangyulin.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import org.apache.struts2.ServletActionContext;
import java.util.List;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.shuangyulin.dao.ProductInfoDAO;
import com.shuangyulin.domain.ProductInfo;
import com.shuangyulin.dao.ProductClassDAO;
import com.shuangyulin.domain.ProductClass;
import com.shuangyulin.dao.YesOrNoDAO;
import com.shuangyulin.domain.YesOrNo;
import com.shuangyulin.test.TestUtil;

public class ProductInfoAction extends ActionSupport {

/*图片字段productPhoto参数接收*/
	 private File productPhotoFile;
	 private String productPhotoFileFileName;
	 private String productPhotoFileContentType;
	 public File getProductPhotoFile() {
		return productPhotoFile;
	}
	public void setProductPhotoFile(File productPhotoFile) {
		this.productPhotoFile = productPhotoFile;
	}
	public String getProductPhotoFileFileName() {
		return productPhotoFileFileName;
	}
	public void setProductPhotoFileFileName(String productPhotoFileFileName) {
		this.productPhotoFileFileName = productPhotoFileFileName;
	}
	public String getProductPhotoFileContentType() {
		return productPhotoFileContentType;
	}
	public void setProductPhotoFileContentType(String productPhotoFileContentType) {
		this.productPhotoFileContentType = productPhotoFileContentType;
	}
    /*界面层需要查询的属性: 商品编号*/
    private String productNo;
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }
    public String getProductNo() {
        return this.productNo;
    }

    /*界面层需要查询的属性: 商品类别*/
    private ProductClass productClassObj;
    public void setProductClassObj(ProductClass productClassObj) {
        this.productClassObj = productClassObj;
    }
    public ProductClass getProductClassObj() {
        return this.productClassObj;
    }

    /*界面层需要查询的属性: 商品名称*/
    private String productName;
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductName() {
        return this.productName;
    }

    /*界面层需要查询的属性: 是否推荐*/
    private YesOrNo recommendFlag;
    public void setRecommendFlag(YesOrNo recommendFlag) {
        this.recommendFlag = recommendFlag;
    }
    public YesOrNo getRecommendFlag() {
        return this.recommendFlag;
    }

    /*界面层需要查询的属性: 上架日期*/
    private String onlineDate;
    public void setOnlineDate(String onlineDate) {
        this.onlineDate = onlineDate;
    }
    public String getOnlineDate() {
        return this.onlineDate;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    ProductInfoDAO productInfoDAO = new ProductInfoDAO();

    /*待操作的ProductInfo对象*/
    private ProductInfo productInfo;
    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }
    public ProductInfo getProductInfo() {
        return this.productInfo;
    }

    /*跳转到添加ProductInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的ProductClass信息*/
        ProductClassDAO productClassDAO = new ProductClassDAO();
        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        /*查询所有的YesOrNo信息*/
        YesOrNoDAO yesOrNoDAO = new YesOrNoDAO();
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryAllYesOrNoInfo();
        ctx.put("yesOrNoList", yesOrNoList);
        return "add_view";
    }

    /*添加ProductInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddProductInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*验证商品编号是否已经存在*/
        String productNo = productInfo.getProductNo();
        ProductInfo db_productInfo = productInfoDAO.GetProductInfoByProductNo(productNo);
        if(null != db_productInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("该商品编号已经存在!"));
            return "error";
        }
        try {
            if(true) {
            ProductClassDAO productClassDAO = new ProductClassDAO();
            ProductClass productClassObj = productClassDAO.GetProductClassByClassId(productInfo.getProductClassObj().getClassId());
            productInfo.setProductClassObj(productClassObj);
            }
            if(true) {
            YesOrNoDAO yesOrNoDAO = new YesOrNoDAO();
            YesOrNo recommendFlag = yesOrNoDAO.GetYesOrNoById(productInfo.getRecommendFlag().getId());
            productInfo.setRecommendFlag(recommendFlag);
            }
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*处理图片上传*/
            String productPhotoFileName = ""; 
       	 	if(productPhotoFile != null) {
       	 		InputStream is = new FileInputStream(productPhotoFile);
       			String fileContentType = this.getProductPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg")  || fileContentType.equals("image/pjpeg"))
       				productPhotoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				productPhotoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				ctx.put("error",  java.net.URLEncoder.encode("上传图片格式不正确!"));
       				return "error";
       			}
       			File file = new File(path, productPhotoFileName);
       			OutputStream os = new FileOutputStream(file);
       			byte[] b = new byte[1024];
       			int bs = 0;
       			while ((bs = is.read(b)) > 0) {
       				os.write(b, 0, bs);
       			}
       			is.close();
       			os.close();
       	 	}
            if(productPhotoFile != null)
            	productInfo.setProductPhoto("upload/" + productPhotoFileName);
            else
            	productInfo.setProductPhoto("upload/NoImage.jpg");
            productInfoDAO.AddProductInfo(productInfo);
            ctx.put("message",  java.net.URLEncoder.encode("ProductInfo添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductInfo添加失败!"));
            return "error";
        }
    }

    /*查询ProductInfo信息*/
    public String QueryProductInfo() {
        if(currentPage == 0) currentPage = 1;
        if(productNo == null) productNo = "";
        if(productName == null) productName = "";
        if(onlineDate == null) onlineDate = "";
        List<ProductInfo> productInfoList = productInfoDAO.QueryProductInfoInfo(productNo, productClassObj, productName, recommendFlag, onlineDate, currentPage);
        /*计算总的页数和总的记录数*/
        productInfoDAO.CalculateTotalPageAndRecordNumber(productNo, productClassObj, productName, recommendFlag, onlineDate);
        /*获取到总的页码数目*/
        totalPage = productInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = productInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("productInfoList",  productInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("productNo", productNo);
        ctx.put("productClassObj", productClassObj);
        ProductClassDAO productClassDAO = new ProductClassDAO();
        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        ctx.put("productName", productName);
        ctx.put("recommendFlag", recommendFlag);
        YesOrNoDAO yesOrNoDAO = new YesOrNoDAO();
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryAllYesOrNoInfo();
        ctx.put("yesOrNoList", yesOrNoList);
        ctx.put("onlineDate", onlineDate);
        return "query_view";
    }

    /*前台查询ProductInfo信息*/
    public String FrontQueryProductInfo() {
        if(currentPage == 0) currentPage = 1;
        if(productNo == null) productNo = "";
        if(productName == null) productName = "";
        if(onlineDate == null) onlineDate = "";
        List<ProductInfo> productInfoList = productInfoDAO.QueryProductInfoInfo(productNo, productClassObj, productName, recommendFlag, onlineDate, currentPage);
        /*计算总的页数和总的记录数*/
        productInfoDAO.CalculateTotalPageAndRecordNumber(productNo, productClassObj, productName, recommendFlag, onlineDate);
        /*获取到总的页码数目*/
        totalPage = productInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = productInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("productInfoList",  productInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("productNo", productNo);
        ctx.put("productClassObj", productClassObj);
        ProductClassDAO productClassDAO = new ProductClassDAO();
        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        ctx.put("productName", productName);
        ctx.put("recommendFlag", recommendFlag);
        YesOrNoDAO yesOrNoDAO = new YesOrNoDAO();
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryAllYesOrNoInfo();
        ctx.put("yesOrNoList", yesOrNoList);
        ctx.put("onlineDate", onlineDate);
        return "front_query_view";
    }

    /*查询要修改的ProductInfo信息*/
    public String ModifyProductInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键productNo获取ProductInfo对象*/
        ProductInfo productInfo = productInfoDAO.GetProductInfoByProductNo(productNo);

        ProductClassDAO productClassDAO = new ProductClassDAO();
        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        YesOrNoDAO yesOrNoDAO = new YesOrNoDAO();
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryAllYesOrNoInfo();
        ctx.put("yesOrNoList", yesOrNoList);
        ctx.put("productInfo",  productInfo);
        return "modify_view";
    }

    /*查询要修改的ProductInfo信息*/
    public String FrontShowProductInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键productNo获取ProductInfo对象*/
        ProductInfo productInfo = productInfoDAO.GetProductInfoByProductNo(productNo);

        ProductClassDAO productClassDAO = new ProductClassDAO();
        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        YesOrNoDAO yesOrNoDAO = new YesOrNoDAO();
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryAllYesOrNoInfo();
        ctx.put("yesOrNoList", yesOrNoList);
        ctx.put("productInfo",  productInfo);
        return "front_show_view";
    }

    /*更新修改ProductInfo信息*/
    public String ModifyProductInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            if(true) {
            ProductClassDAO productClassDAO = new ProductClassDAO();
            ProductClass productClassObj = productClassDAO.GetProductClassByClassId(productInfo.getProductClassObj().getClassId());
            productInfo.setProductClassObj(productClassObj);
            }
            if(true) {
            YesOrNoDAO yesOrNoDAO = new YesOrNoDAO();
            YesOrNo recommendFlag = yesOrNoDAO.GetYesOrNoById(productInfo.getRecommendFlag().getId());
            productInfo.setRecommendFlag(recommendFlag);
            }
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*处理图片上传*/
            String productPhotoFileName = ""; 
       	 	if(productPhotoFile != null) {
       	 		InputStream is = new FileInputStream(productPhotoFile);
       			String fileContentType = this.getProductPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg") || fileContentType.equals("image/pjpeg"))
       				productPhotoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				productPhotoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				ctx.put("error",  java.net.URLEncoder.encode("上传图片格式不正确!"));
       				return "error";
       			}
       			File file = new File(path, productPhotoFileName);
       			OutputStream os = new FileOutputStream(file);
       			byte[] b = new byte[1024];
       			int bs = 0;
       			while ((bs = is.read(b)) > 0) {
       				os.write(b, 0, bs);
       			}
       			is.close();
       			os.close();
            productInfo.setProductPhoto("upload/" + productPhotoFileName);
       	 	}
            productInfoDAO.UpdateProductInfo(productInfo);
            ctx.put("message",  java.net.URLEncoder.encode("ProductInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除ProductInfo信息*/
    public String DeleteProductInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            productInfoDAO.DeleteProductInfo(productNo);
            ctx.put("message",  java.net.URLEncoder.encode("ProductInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductInfo删除失败!"));
            return "error";
        }
    }

}
