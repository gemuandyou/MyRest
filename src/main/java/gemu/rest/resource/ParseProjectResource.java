package gemu.rest.resource;

import gemu.rest.annotation.MyUrl;
import gemu.rest.app.ClassTree;
import gemu.rest.app.GetAllClassForProject;
import gemu.rest.core.AmusingProperties;
import gemu.rest.core.MyRestParams;
import gemu.rest.core.RequestMethod;
import gemu.rest.core.ReturnType;
import gemu.rest.log.JavaLog;
import gemu.rest.tool.auxiliary.JsonStringFormatParse;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 解析Java项目
 */
@MyUrl("/project")
public class ParseProjectResource {

    GetAllClassForProject currObj = new GetAllClassForProject();

    @MyUrl(value = "/parseClassRlt", type = ReturnType.STRING, method = RequestMethod.POST)
    public String parseClassRlt(HttpServletResponse response, MyRestParams paramsObj) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(paramsObj);
        response.setCharacterEncoding("GBK");
        try {
            GetAllClassForProject.srcPath = paramsObj.getFormParams().get("filePath")[0];
            ClassTree classTree = GetAllClassForProject.getClassTree();
            File file = new File(AmusingProperties.TREE_DATE_FILE);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(JsonStringFormatParse.formatParse(classTree.toString()));
            fileWriter.flush();
            fileWriter.close();
            return classTree.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JavaLog.issue(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            JavaLog.issue(e.getMessage());
        }
        return "{}";
    }

}
