package com.luzm.amis.gen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.luzm.amis.gen.anotations.AmisCloumnLabel;
import com.luzm.amis.gen.bean.Body;
import com.luzm.amis.gen.bean.BodyItem;
import com.luzm.amis.gen.bean.ButtonsItem;
import com.luzm.amis.gen.bean.ColumnsItem;
import com.luzm.amis.gen.bean.ControlsItem;
import com.luzm.amis.gen.bean.Dialog;
import com.luzm.amis.gen.bean.Drawer;
import com.luzm.amis.gen.bean.Filter;
import com.luzm.amis.gen.bean.Response;
import com.luzm.amis.gen.config.AmisService;
import com.luzm.amis.gen.config.GenerationConfiguration;
import com.luzm.amis.gen.show.ControlsItemTypeEnum;
import com.luzm.amis.gen.show.ShowColumnGenServiceManager;
import com.thoughtworks.xstream.XStream;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

//@Mojo(name = "amisgen-goal")
public class GenAmisMain {
//    @Parameter(defaultValue = "${project}", readonly = true, required = true)
//    private MavenProject project;
//
//    @SneakyThrows
//    @Override
//    public void execute() throws MojoExecutionException, MojoFailureException {
//        GenerationConfiguration genConfig = getGenConfig();
//        buildService(genConfig);
//        buildController(genConfig);
//        buildAmisJsonFile(genConfig);
//
//    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        gen();
    }

    public static void gen() throws ClassNotFoundException {
        GenerationConfiguration genConfig = getGenConfig();
        buildService(genConfig);
        buildController(genConfig);
        buildAmisJsonFile(genConfig);
    }

    private static GenerationConfiguration getGenConfig() {
        XStream stream = new XStream();
        XStream.setupDefaultSecurity(stream);
        stream.allowTypes(new Class[]{GenerationConfiguration.class, AmisService.class});
        stream.setClassLoader(GenerationConfiguration.class.getClassLoader());
        stream.processAnnotations(new Class[]{GenerationConfiguration.class, AmisService.class});
        GenerationConfiguration generationConfiguration = (GenerationConfiguration) stream.fromXML(new File("src/main/resources/dev/amis-generator.xml"));
        return generationConfiguration;
    }

    private static void buildController(GenerationConfiguration generationConfiguration) {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        Template template = ve.getTemplate("controller.vm");
        List<AmisService> amisServices = generationConfiguration.getAmis();
        for (AmisService amisService : amisServices) {
            VelocityContext context = new VelocityContext();
            context.put("package", generationConfiguration.getControllerPackage());
            context.put("controllerMapping", amisService.getControllerMapping());
            context.put("controllerName", amisService.getControllerName());
            context.put("serviceName", generationConfiguration.getServicePackage() + "." + amisService.getServiceName());
            context.put("dao", amisService.getDaoClazzName());
            // 实例化一个StringWriter
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            genJavaFile(generationConfiguration.getControllerPackage(), amisService.getControllerName(), writer.toString());
        }

    }

    private static void buildService(GenerationConfiguration generationConfiguration) {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        Template template = ve.getTemplate("service.vm");

        List<AmisService> amisServices = generationConfiguration.getAmis();
        for (AmisService amisService : amisServices) {
            VelocityContext context = new VelocityContext();
            context.put("package", generationConfiguration.getServicePackage());
            context.put("serviceName", amisService.getServiceName());
            context.put("dao", amisService.getDaoClazzName());
            context.put("mapper", amisService.getMapperClazzName());
            context.put("example", amisService.getExampleClazzName());
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            genJavaFile(generationConfiguration.getServicePackage(), amisService.getServiceName(), writer.toString());
        }
    }

    private static void genJavaFile(String packageStr, String fileName, String content) {
        try {
            String genFileDir = "src/main/java/" + packageStr.replace(".", "/");
            File directory = new File(genFileDir);
            directory.mkdirs();

            String targetFile = genFileDir + "/" + fileName + ".java";
            writeContentToFile(content, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void genAmisFile(String content, String fileName) {
        try {
            String genFileDir = "src/main/resources/";
            File directory = new File(genFileDir);
            directory.mkdirs();
            String targetFile = genFileDir + "/" + fileName + ".json";
            writeContentToFile(content, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeContentToFile(String content, String targetFile) throws IOException {
        File file = new File(targetFile);
        if (file.exists()) {
            file.delete();
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(new File(targetFile));
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();
    }


    private static void buildAmisJsonFile(GenerationConfiguration genConfig) throws ClassNotFoundException {
        String apiPrefix = "post:" + "${" + genConfig.getApiHost() + "}";


        List<AmisService> amis = genConfig.getAmis();
        for (AmisService amisService : amis) {
            Class<?> clazz = Class.forName(amisService.getDaoClazzName());
            Field[] declaredFields = clazz.getDeclaredFields();
            Response amisCurdPage = new Response();
            amisCurdPage.setType("page");
            amisCurdPage.setTitle(amisService.getServiceName());
            amisCurdPage.setBody(new ArrayList<>());

            BodyItem addItem = new BodyItem();
            addItem.setType("button");
            addItem.setLabel("新增");
            addItem.setActionType("dialog");
            addItem.setClassName("m-b-sm");
            addItem.setLevel("primary");
            addItem.setDialog(new Dialog());
            addItem.getDialog().setTitle("新增数据");
            addItem.getDialog().setBody(new Body());
            addItem.getDialog().getBody().setApi(apiPrefix + amisService.getControllerMapping() + "/add");
            addItem.getDialog().getBody().setType("form");
            addItem.getDialog().getBody().setControls(new ArrayList<>());
            amisCurdPage.getBody().add(addItem);


            BodyItem curdItem = new BodyItem();
            curdItem.setType("crud");
            curdItem.setApi(apiPrefix + amisService.getControllerMapping() + "/list");
            curdItem.setSyncLocation(false);
            curdItem.setColumns(new ArrayList<>());

            curdItem.setFilter(new Filter());
            curdItem.getFilter().setTitle("筛选");
            // 筛选条件
            curdItem.getFilter().setControls(new ArrayList<>());
            // 展示列
            amisCurdPage.getBody().add(curdItem);
            for (Field field : declaredFields) {
                String name = field.getName();
                String label = name.toUpperCase();
                if (field.isAnnotationPresent(AmisCloumnLabel.class)) {
                    AmisCloumnLabel amisCloumnLabel = field.getAnnotation(AmisCloumnLabel.class);
                    if (amisCloumnLabel.label() != null && !"".equals(amisCloumnLabel.label())) {
                        label = amisCloumnLabel.label();
                    }

                }
                // 1、初始化AddItem
                List<ControlsItem> controls = addItem.getDialog().getBody().getControls();
                ControlsItem controlsItem = buildAddItem(field, label);
                if (controlsItem != null) {
                    controls.add(controlsItem);
                }
                // 2、筛选条件
                List<ControlsItem> filterControlsItems = curdItem.getFilter().getControls();

                ControlsItem filterControlsItem = buildFilterItem(field, label);
                filterControlsItems.add(filterControlsItem);
                // 3、展示列
                List<ColumnsItem> curdColumns = curdItem.getColumns();
                ColumnsItem columnsItem = ShowColumnGenServiceManager.getInstance()
                        .getShowColumnService(field).gen(field);
                curdColumns.add(columnsItem);
            }

            // 操作
            ColumnsItem operationColumnsItem = new ColumnsItem();
            operationColumnsItem.setType("operation");
            operationColumnsItem.setName("操作");
            operationColumnsItem.setButtons(new ArrayList<>());
            curdItem.getColumns().add(operationColumnsItem);

            ButtonsItem deleteButton = new ButtonsItem();
            deleteButton.setLabel("删除");
            deleteButton.setType("button");
            deleteButton.setActionType("ajax");
            deleteButton.setConfirmText("确认要删除?");
            deleteButton.setApi(apiPrefix + amisService.getControllerMapping() + "/delete/{id}");
            operationColumnsItem.getButtons().add(deleteButton);

            ButtonsItem updateButton = new ButtonsItem();
            updateButton.setLabel("修改");
            updateButton.setApi(apiPrefix + amisService.getControllerMapping() + "/update");
            updateButton.setType("button");
            updateButton.setActionType("drawer");
            updateButton.setDrawer(new Drawer());
            updateButton.getDrawer().setTitle("修改${id} 行数据");
            updateButton.getDrawer().setBody(new Body());
            updateButton.getDrawer().getBody().setType("form");
            updateButton.getDrawer().getBody().setType("crud");
            updateButton.getDrawer().getBody().setControls(addItem.getDialog().getBody().getControls());
            operationColumnsItem.getButtons().add(updateButton);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            genAmisFile(gson.toJson(amisCurdPage), amisService.getServiceName());
        }


    }

    private static ControlsItem buildFilterItem(Field field, String label) {
        ControlsItem filterControlsItem = new ControlsItem();
        filterControlsItem.setLabel(label);
        filterControlsItem.setName(field.getName());
        Class<?> fieldType = field.getType();
        ControlsItemTypeEnum controlsItemTypeEnum = ControlsItemTypeEnum.getByClazz(fieldType);
        filterControlsItem.setType(controlsItemTypeEnum.getInputType());
        return filterControlsItem;
    }

    /**
     * 1、针对AddItem,ID 列不自动生成
     *
     * @param field
     * @param label
     * @return
     */
    private static ControlsItem buildAddItem(Field field, String label) {
        String name = field.getName();
        Class<?> fieldType = field.getType();
        if ("id".equals(name)) {
            return null;
        }
        ControlsItem controlsItem = new ControlsItem();
        controlsItem.setLabel(label);
        controlsItem.setName(name);
        controlsItem.setRequired(true);
        ControlsItemTypeEnum controlsItemTypeEnum = ControlsItemTypeEnum.getByClazz(fieldType);
        controlsItem.setType(controlsItemTypeEnum.getInputType());
        return controlsItem;
    }


}