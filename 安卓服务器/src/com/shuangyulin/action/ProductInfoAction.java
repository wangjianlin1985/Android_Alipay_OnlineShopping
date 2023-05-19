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

/*ͼƬ�ֶ�productPhoto��������*/
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
    /*�������Ҫ��ѯ������: ��Ʒ���*/
    private String productNo;
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }
    public String getProductNo() {
        return this.productNo;
    }

    /*�������Ҫ��ѯ������: ��Ʒ���*/
    private ProductClass productClassObj;
    public void setProductClassObj(ProductClass productClassObj) {
        this.productClassObj = productClassObj;
    }
    public ProductClass getProductClassObj() {
        return this.productClassObj;
    }

    /*�������Ҫ��ѯ������: ��Ʒ����*/
    private String productName;
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductName() {
        return this.productName;
    }

    /*�������Ҫ��ѯ������: �Ƿ��Ƽ�*/
    private YesOrNo recommendFlag;
    public void setRecommendFlag(YesOrNo recommendFlag) {
        this.recommendFlag = recommendFlag;
    }
    public YesOrNo getRecommendFlag() {
        return this.recommendFlag;
    }

    /*�������Ҫ��ѯ������: �ϼ�����*/
    private String onlineDate;
    public void setOnlineDate(String onlineDate) {
        this.onlineDate = onlineDate;
    }
    public String getOnlineDate() {
        return this.onlineDate;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    ProductInfoDAO productInfoDAO = new ProductInfoDAO();

    /*��������ProductInfo����*/
    private ProductInfo productInfo;
    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }
    public ProductInfo getProductInfo() {
        return this.productInfo;
    }

    /*��ת�����ProductInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�ProductClass��Ϣ*/
        ProductClassDAO productClassDAO = new ProductClassDAO();
        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        /*��ѯ���е�YesOrNo��Ϣ*/
        YesOrNoDAO yesOrNoDAO = new YesOrNoDAO();
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryAllYesOrNoInfo();
        ctx.put("yesOrNoList", yesOrNoList);
        return "add_view";
    }

    /*���ProductInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddProductInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤��Ʒ����Ƿ��Ѿ�����*/
        String productNo = productInfo.getProductNo();
        ProductInfo db_productInfo = productInfoDAO.GetProductInfoByProductNo(productNo);
        if(null != db_productInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("����Ʒ����Ѿ�����!"));
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
            /*����ͼƬ�ϴ�*/
            String productPhotoFileName = ""; 
       	 	if(productPhotoFile != null) {
       	 		InputStream is = new FileInputStream(productPhotoFile);
       			String fileContentType = this.getProductPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg")  || fileContentType.equals("image/pjpeg"))
       				productPhotoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				productPhotoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				ctx.put("error",  java.net.URLEncoder.encode("�ϴ�ͼƬ��ʽ����ȷ!"));
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
            ctx.put("message",  java.net.URLEncoder.encode("ProductInfo��ӳɹ�!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯProductInfo��Ϣ*/
    public String QueryProductInfo() {
        if(currentPage == 0) currentPage = 1;
        if(productNo == null) productNo = "";
        if(productName == null) productName = "";
        if(onlineDate == null) onlineDate = "";
        List<ProductInfo> productInfoList = productInfoDAO.QueryProductInfoInfo(productNo, productClassObj, productName, recommendFlag, onlineDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        productInfoDAO.CalculateTotalPageAndRecordNumber(productNo, productClassObj, productName, recommendFlag, onlineDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = productInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*ǰ̨��ѯProductInfo��Ϣ*/
    public String FrontQueryProductInfo() {
        if(currentPage == 0) currentPage = 1;
        if(productNo == null) productNo = "";
        if(productName == null) productName = "";
        if(onlineDate == null) onlineDate = "";
        List<ProductInfo> productInfoList = productInfoDAO.QueryProductInfoInfo(productNo, productClassObj, productName, recommendFlag, onlineDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        productInfoDAO.CalculateTotalPageAndRecordNumber(productNo, productClassObj, productName, recommendFlag, onlineDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = productInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�ProductInfo��Ϣ*/
    public String ModifyProductInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������productNo��ȡProductInfo����*/
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

    /*��ѯҪ�޸ĵ�ProductInfo��Ϣ*/
    public String FrontShowProductInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������productNo��ȡProductInfo����*/
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

    /*�����޸�ProductInfo��Ϣ*/
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
            /*����ͼƬ�ϴ�*/
            String productPhotoFileName = ""; 
       	 	if(productPhotoFile != null) {
       	 		InputStream is = new FileInputStream(productPhotoFile);
       			String fileContentType = this.getProductPhotoFileContentType();
       			if(fileContentType.equals("image/jpeg") || fileContentType.equals("image/pjpeg"))
       				productPhotoFileName = UUID.randomUUID().toString() +  ".jpg";
       			else if(fileContentType.equals("image/gif"))
       				productPhotoFileName = UUID.randomUUID().toString() +  ".gif";
       			else {
       				ctx.put("error",  java.net.URLEncoder.encode("�ϴ�ͼƬ��ʽ����ȷ!"));
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
            ctx.put("message",  java.net.URLEncoder.encode("ProductInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��ProductInfo��Ϣ*/
    public String DeleteProductInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            productInfoDAO.DeleteProductInfo(productNo);
            ctx.put("message",  java.net.URLEncoder.encode("ProductInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
