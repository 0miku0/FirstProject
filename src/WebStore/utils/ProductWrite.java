package WebStore.utils;

import WebStore.bean.Product;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class ProductWrite {
    List<FileItem> items;
    String contextRealPath;
    String contextRelativePath;
    HashMap<String, String> productMap = new HashMap<>();

    public ProductWrite(List<FileItem> items, String contextRealPath, String contextRelativePath) {
        this.items = items;
        this.contextRealPath = contextRealPath;
        this.contextRelativePath = contextRelativePath;
    }

    public Product getProduct() throws SQLException {
        Product product = new Product();
        product.setPid(Integer.parseInt(productMap.get("pid")));
        product.setPname(productMap.get("pname"));
        product.setDesc(productMap.get("desc"));
        product.setImgurl(productMap.get("imgurl"));
        product.setCid(Integer.parseInt(productMap.get("cid")));
        product.setPnum(Integer.parseInt(productMap.get("pnum")));
        product.setEstoreprice(Double.parseDouble(productMap.get("estoreprice")));
        product.setMarkprice(Double.parseDouble(productMap.get("markprice")));
        return product;
    }

    public void handle() throws Exception {
        Iterator<FileItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            FileItem item = iterator.next();
            if (item.isFormField()) {
                //处理普通表单项
                processFormField(item);
            } else {
                //处理文件表单项
                processUploadedFile(item);
            }
        }
    }

    private void processUploadedFile(FileItem item) throws Exception {
        String fileName = item.getName();
        //.是转义字符 需要加\\
        String[] split = fileName.split("\\.");
        String end=split[split.length-1];
        String uuid = UUID.randomUUID().toString();
        //改用UUID，解决请求资源的中文解码//字符串拼接不需要转义
        fileName = uuid+"."+end;
        if (item.getSize()==0){
            return;
        }
        String fileRealPath =contextRealPath+"/"+fileName;
        File uploadedFile = new File(fileRealPath);
        if(!uploadedFile.exists()){
            uploadedFile.mkdirs();
        }
        item.write(uploadedFile);
//        String fileRelativePath=contextRelativePath+"/"+fileName;
        productMap.put(item.getFieldName(),fileName);
    }

    private void processFormField(FileItem item) throws UnsupportedEncodingException {
        productMap.put(item.getFieldName(),item.getString("utf-8"));
    }

}
