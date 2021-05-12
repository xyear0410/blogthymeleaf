package com.tgxyear.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tgxyear.mapper.*;
import com.tgxyear.pojo.*;
import com.tgxyear.service.Blogservice;
import com.tgxyear.service.Commentservice;
import com.tgxyear.service.UBlinkservice;
import com.tgxyear.utils.Arris;
import com.tgxyear.utils.BlogUser;
import com.tgxyear.utils.SearchUser;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


//前端页面展示
@Controller
public class webfrontcontroller {

    SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd");
    boolean  read;
    private int bidmsg;

    @Autowired
    BlogMapper blogMapper;
    @Autowired
    UpicMapper upicMapper;
    @Autowired
    Blogservice blogservice;
    @Autowired
    UBlinkMapper uBlinkMapper;
    @Autowired
    UBlinkservice uBlinkservice;
    @Autowired
    FollowMapper followMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    GreatblogMapper greatblogMapper;
    @Autowired
    Commentservice commentservice;
    @Autowired
    BclassifylinkMapper bc ;
    @Autowired
    ClassifyMapper classifyMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    Labelmapper labelmapper;
    @Autowired
    Labelidmapper labelidmapper;
    @Autowired
    LabelViewMapper labelViewMapper;

    int labelbid;
    int a1=0;
    int a2=0;
    int a3=0;
    int a4=0;

    private Object Arris;

    //博客详情页面
    @GetMapping("/toblogmessage/{id}")
    public String  toblogindex(@PathVariable int id,Model model){
        //取得值
        String o = SecurityContextHolder.getContext().getAuthentication().getName();
        //进来的用户
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=(User) principal;

        model.addAttribute("comment",user.say());
        model.addAttribute("ads",o);
        //获取该博客id
        model.addAttribute("kos",id);
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq("id",id);
        //获取博客信息，并更新浏览次数
        Blog one = blogservice.getOne(wrapper);
        one.setView(one.getView()+1);
        blogservice.updateById(one);
        model.addAttribute("blogones",one);

        //原作者信息
        User user1 = userMapper.selectoneuser(one.getUid());
        model.addAttribute("user",user1);
        //查出关注数
        QueryWrapper<Follow> wrapper2 = new QueryWrapper();
        wrapper2.eq("noticeid",user1.getId());
        System.out.println("收集数=》"+followMapper.selectCount(wrapper2));
        model.addAttribute("noticeid", followMapper.selectCount(wrapper2));


        //辨别关注者或者是本人
        //判断是否为本人
        //博客文章的作者id
        model.addAttribute("in",one.getUid());
        //进来的人的id
        model.addAttribute("out",user.getId());

        //关注的确认
        QueryWrapper<Follow> followwrapper =new QueryWrapper();
        //进来的人
        followwrapper.eq("followid",user.getId())
                    .eq("noticeid",one.getUid());                    //原本的人
        Integer followcount = followMapper.selectCount(followwrapper);
        if (followcount==0){
            //表示该进来的人没有关注
            model.addAttribute("followcount",0);
        }else {
            model.addAttribute("followcount",1);
        }
        //评论用户的id 用于评论表单提交

        model.addAttribute("uid",user.getId());


        //有点偷懒了 ，在这里直接偷值,用于下面的列表局部刷新
        bidmsg =id;
        //标签
        String classifyname = classifyMapper.selectByid(id).getClassifyname();
        model.addAttribute("Bclinks",classifyname);

        //点赞的值确认

//        System.out.println("bid=>"+id+"uid=>"+user.getId());
        int live;
        System.out.println("=========开始点赞=========");
        QueryWrapper<Greatblog> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("uid",user.getId())
                .eq("bid",bidmsg);
        System.out.println("==========条件补充完毕===========");
        System.out.println(greatblogMapper.selectOne(queryWrapper));
        if (greatblogMapper.selectOne(queryWrapper)!=null){
            System.out.println("不为空");
            live =1;
            model.addAttribute("live",live);
        }else if (greatblogMapper.selectOne(queryWrapper)==null){
            System.out.println("为空");
            live =0;
            model.addAttribute("live",live);
        }
        System.out.println("============完成=========");

//        model.addAttribute("live",1);
        System.out.println("bid的值"+bidmsg);
        //标签
        List<Label> getbloglabel = labelmapper.getbloglabel(id);
        //上面是获取想对应的博客标签
        //这里是显示
        model.addAttribute("getbloglabel",getbloglabel);

        System.out.println("getbloglabel=>"+getbloglabel);


        //用于推荐存储
        List<Blog> records;
        //为空判断 针对新用户
        if(getbloglabel.isEmpty()){
            System.out.println("为空");
            QueryWrapper<Blog> maxgreat =new QueryWrapper<>();
            maxgreat.orderByDesc("bgreat");
            Page<Blog> page =new Page<>(1,8);
            records = blogMapper.selectPage(page, maxgreat).getRecords();
        }else {
            //标签得话可能有多个 这里取第一个 也就是0
            System.out.println(getbloglabel.get(0).getId());
           records= labelidmapper.getbattle(getbloglabel.get(0).getId());
            System.out.println("不为空");
        }
//        System.out.println("records=>"+records);
        model.addAttribute("recommends",records);

        //插入用户所看博客的标签
        for (Label label : getbloglabel) {
            Labelview view = new Labelview();
            view.setUid(user.getId());
            view.setLid(label.getId());
            int insert = labelViewMapper.insert(view);
            System.out.println("修改了"+insert+"行");
        }


        return "blogmessage";
    }



    //跳转博客添加页面
    @GetMapping("/toaddbloguser")
    public String toaddbloguser(Model model){
        a1 =0;
        a2 =0;
        a3 =0;
        a4 =0;
        //用户姓名
        String o = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("ads",o);
        List<Classify> classifies = classifyMapper.selectList(null);
        model.addAttribute("classifies",classifies);

        //标签选择
        List<Label> labels = labelmapper.selectList(null);
        model.addAttribute("labels",labels);

        return "blogadd";
    }

    //标签
    @PostMapping("/selectlabel")
    @ResponseBody
    public void selectlabel(int[] group) throws IOException{
        System.out.println("group=>"+group);
        int check =1;

        for (int id: group){
            System.out.println("当前为"+id);
            if (a1 ==0){
                a1 =id;
                continue;
            }
            if (a1 !=0){
                a2 =id;
                continue;
            }

            System.out.println("a1=>"+a1);
        }
        System.out.println("a1=>"+a1);
        System.out.println("a2=>"+a2);
        System.out.println("a3=>"+a3);
        System.out.println("a4=>"+a4);

    labelbid =1;

    }


    //博客添加
    @SneakyThrows
    @PostMapping("/addblogback")
    public String addblogback(Blog blog,
                              @RequestParam(value = "file") MultipartFile file,
                              Model model,
                              RedirectAttributes redirectAttributes) throws IOException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=(User) principal;
        System.out.println("labelbid=>"+labelbid);
        System.out.println("当前用户id" +user.getId());

        System.out.println("======添加==============");
        System.out.println(blog);
        System.out.println(file);
        //保存数据库的路径
        String sqlPath = null;
        //定义文件保存的本地路径
        String localPath = "G:\\imagehome\\";
        //定义 文件名
        String filename = null;
        //定义 返回信息
        String returnmsg ;
        if (!file.isEmpty()) {
            //生成uuid作为文件名称    随机生成的图片名字
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //获得文件类型（可以判断如果不是图片，禁止上传）
            String contentType = file.getContentType();
            //获得文件后缀名获取到jpg或者是其他格式
            String suffixName = contentType.substring(contentType.indexOf("/") + 1);  // mingzi.jpg 保留jpg
            //得到 文件名  一群随机数字加上他的格式 如xxxxx.jpg
            filename = uuid + "." + suffixName;
            System.out.println("该图片的文件名字"+filename);
            //文件保存路径  将图片保存在D盘的file中
            file.transferTo(new File(localPath + filename));
        } else {
            System.out.println("文件为空");
        }
        //把图片的相对路径保存至数据库
        sqlPath = "/image/" + filename;

        System.out.println(sqlPath);
        blog.setPicture(sqlPath);
        //博客表
        blog.setUid(user.getId());
        boolean save = blogservice.save(blog);
        if (save){
            System.out.println("添加成功");
            returnmsg="yes";

        }else {
            returnmsg=null;
            System.out.println("添加失败");
        }
        redirectAttributes.addFlashAttribute("addblogmsg",returnmsg);

        //标签增加
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.eq("title",blog.getTitle())
                        .eq("uid",user.getId());
        Blog blog1 = blogMapper.selectOne(blogQueryWrapper);
        System.out.println("blog添加后的"+blog1);
        System.out.println("bid=>"+blog.getId());
        System.out.println();
        System.out.println("======结束==============");
        Labelid labelid = new Labelid();
        //wuwuwuwuw
        if (a1!=0){
            labelid.setBid(blog1.getId());
            labelid.setLabelid(a1);
            labelidmapper.insert(labelid);
            a1 =0;
        }
        if (a2!=0){
            labelid.setBid(blog1.getId());
            labelid.setLabelid(a2);
            labelidmapper.insert(labelid);
            a2 =0;
        }
        if (a3!=0){
            labelid.setBid(blog1.getId());
            labelid.setLabelid(a3);
            labelidmapper.insert(labelid);
            a3 =0;
        }
        if (a4!=0){
            labelid.setBid(blog1.getId());
            labelid.setLabelid(a4);
            labelidmapper.insert(labelid);
            a4 =0;
        }

        return "redirect:/toblogdindex";
    }



    //某博客评论列表显示
    @GetMapping("/toblogmessage/comment")
    public String commentss(Model model){
        //上边借到的bigmsg
//        System.out.println("偷到的bid的值"+bidmsg);
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq("bid",bidmsg);
        List<Comment> list = commentservice.listComment(bidmsg);
        model.addAttribute("comments",list);
        return "blogmessage :: commentList";
    }

//博客页面上---添加评论
    @PostMapping("/addcomment")
    public String addcomment(Comment comment){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=(User) principal;
//        System.out.println("当前用户为"+name);
        comment.setUname(user.getName());
        System.out.println(comment);

       if (comment.getParentComment().getId() != 0)
       {
            comment.setParentCommentId(comment.getParentComment().getId());
           System.out.println("当前id为"+comment.getParentCommentId());
       }

        commentservice.saveComment(comment);


        return "redirect:/toblogmessage/comment";
    }

    //点赞
    @PostMapping("/like")
    @ResponseBody
    public int like(int bid,int index){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=(User) principal;
        System.out.println("userid=>"+user.getId());
        int uid =user.getId();
        if (index ==1){
            //用户点赞了
            Greatblog greatblog =new Greatblog();
            greatblog.setBid(bid);
            greatblog.setUid(uid);
            greatblogMapper.insert(greatblog);
        }else if (index ==0){
            //用户取消点赞
            greatblogMapper.delall(bid,uid);
        }
        //取得表的值
        //点赞表
        int output = greatblogMapper.selectblogonegreat(bid);
        //博客表得更新
        blogMapper.updateBgreatById(output,bid);
        return output;
    }

    //顶部
    @GetMapping("/toppersonpage")
    public  String toppersonpage(Model model,   @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "4")int pageSize){
        //当前用户信息获取
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=(User) principal;
        //用户信息传参
        model.addAttribute("user",user);
        //找出相对应人的博客
        IPage<Blog> page =new Page<>(pageNum,pageSize);
        List<Blog> lists = blogMapper.selectoneblog(user.getId(),page);
        model.addAttribute("lists",lists);

        //博客分页
        QueryWrapper<Blog> wrapper =new QueryWrapper<>();
        wrapper.eq("uid",user.getId());
        Integer count = blogMapper.selectCount(wrapper);
        float c = (float)count / (float)pageSize;
        double ceil = Math.ceil(c);
        int  a =(int)ceil;
        model.addAttribute("pagecount",a);
        //当前页数
        int currentpage =pageNum;
        model.addAttribute("currentpage",currentpage);


        //粉丝数字
        QueryWrapper<Follow> wrappernotice = new QueryWrapper();
        wrappernotice.eq("noticeid",user.getId());
//        System.out.println( "粉丝数"+followMapper.selectCount(wrappernotice));
        model.addAttribute("noticed", followMapper.selectCount(wrappernotice));

        //关注数字
        QueryWrapper<Follow> wrapperfollow = new QueryWrapper();
        System.out.println("userid"+user.getId());
        wrapperfollow.eq("followid",user.getId());
        model.addAttribute("followid", followMapper.selectCount(wrapperfollow));

        //点赞数

        model.addAttribute("greatnum",greatblogMapper.getusergreat(user.getId()));
        //顶部图片
        Upic upic = upicMapper.selectById(3);
//        System.out.println("=>"+upic);
        model.addAttribute("upic",upic);




        return "personal/topperson";
    }


    //个人主页
    @GetMapping("/topersonpage/{id}")
    public  String topersonpage(@PathVariable("id") int id,Model model){
        //标题栏显示
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=(User) principal;
        model.addAttribute("ads",user.getName());

        //头像
        model.addAttribute("user",user);
        //找出相对应人的博客
        List<Blog> lists = blogMapper.selectoneblog(id);
        model.addAttribute("lists",lists);

        //顶部图片
        Upic upic = upicMapper.selectById(id);
        if (upic ==null){
            Upic pic =new Upic();
            pic.setId(id);
            pic.setUid(id);
            pic.setPic(null);
            upicMapper.insert(pic);
            model.addAttribute("upic",pic);
        }else {
            model.addAttribute("upic",upic);
        }




        //判断是否为本人
        //个人id,上面得id是进入个人界面的id
        User user1 = userMapper.selectoneuser(id);
        model.addAttribute("user1",user1);
        model.addAttribute("in",id);
        //进来的人的id
        model.addAttribute("out",user.getId());



        //查出关注数
        QueryWrapper<Follow> wrapper2 = new QueryWrapper();
        wrapper2.eq("noticeid",user1.getId());
        System.out.println("收集数=》"+followMapper.selectCount(wrapper2));
        model.addAttribute("noticeid", followMapper.selectCount(wrapper2));
        System.out.println("用户编号"+id);


        //关注的确认
        QueryWrapper<Follow> followwrapper =new QueryWrapper();
        //进来的人
        followwrapper.eq("followid",user.getId())
                .eq("noticeid",id);                    //原本的人
        Integer followcount = followMapper.selectCount(followwrapper);
        if (followcount==0){
            //表示该进来的人没有关注
            model.addAttribute("followcount",0);
        }else {
            model.addAttribute("followcount",1);
        }

        //博客点赞数
        int countgreat = greatblogMapper.getusergreat(id);
        model.addAttribute("countgreat",countgreat);

        //
        return "personpage";
    }

    //到个人博客管理空间
    @GetMapping("/topersonspace/{id}")
    public String topersonspace(@PathVariable("id")int id,
                                @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "5")int pageSize,Model model,
                                @ModelAttribute(value ="delblogmessage")String delblogmessage,
                                @ModelAttribute(value = "updatestatus")String updatestatus) {
        //此id 是被进入的id
        model.addAttribute("in",id);
        //标题栏显示
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=(User) principal;
        //用于用户判断
        model.addAttribute("out",user.getId());
        //显示名字
        model.addAttribute("ads",user.getName());

        //查找出in的博客列表
        Page<Blog> blogPage =new Page<>(pageNum,pageSize);
        List<Blog> records = blogservice.selectonePageText(id, blogPage).getRecords();
        model.addAttribute("lists",records);

        //查出所有的类型
        List<Classify> classifies = classifyMapper.selectList(null);
        model.addAttribute("classifies",classifies);


        //分页
        QueryWrapper<Blog> wrapper = new QueryWrapper();
        wrapper.eq("uid",id);
        //        搜索出当前博客的条数
        int count = blogservice.count(wrapper);
        float c = (float)count / (float)pageSize;
//        System.out.println(c);
        double ceil = Math.ceil(c);
//        System.out.println("向上去整数"+ceil);
        int  a =(int)ceil;
        model.addAttribute("pagecount",a);
        //当前页数
//        System.out.println(pageNum);
        int currentpage =pageNum;
        model.addAttribute("currentpage",currentpage);

        //提示的判断
        int delmsg = -1;
        //一开始进来的时候为空 而有参数的时候就
        if (delblogmessage =="yes"){
            delmsg =1;
        }else if (delblogmessage ==null){
            delmsg =0;
        }
        int updatemsg = -1;
        if (updatestatus =="yes"){
            updatemsg =1;
        }else if (updatestatus =="no"){
            updatemsg =0;
        }
        model.addAttribute("delmsg",delmsg);
        System.out.println("delmsg=>"+delmsg);
        System.out.println("delblogmessage=>"+delblogmessage);

        model.addAttribute("updatemsg",updatemsg);
        System.out.println("updatemsg=>"+updatemsg);
        return "personspace";
    }

    //到个人评论页面
    @GetMapping("/personcomment/{id}")
        public  String personcomment(@PathVariable("id")int id,@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "5")int pageSize,Model model){
        //此id 是被进入的id
        model.addAttribute("in",id);
        //标题栏显示
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=(User) principal;
        //用于用户判断
        model.addAttribute("out",user.getId());
        Page<Comment> commentPage =new Page<>(pageNum,pageSize);
        List<Comment> selectusercomment = commentMapper.selectusercomment(commentPage,user.getId());
        model.addAttribute("lists",selectusercomment);

        QueryWrapper<Comment> wrapper =new QueryWrapper<>();
        wrapper.eq("uid",id);
        Integer integer = commentMapper.selectCount(wrapper);
        float c = (float)integer / (float)pageSize;
//        System.out.println(c);
        double ceil = Math.ceil(c);
//        System.out.println("向上去整数"+ceil);
        int  a =(int)ceil;
        model.addAttribute("pagecount",a);
        //当前页数
//        System.out.println(pageNum);
        int currentpage =pageNum;
        model.addAttribute("currentpage",currentpage);


        return "personcomment";
    }

    //到点赞页面
    @GetMapping("/topersongreat/{id}")
    public String topersongreat(@PathVariable("id")int id,@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "5")int pageSize,Model model){
        //此id 是被进入的id
        model.addAttribute("in",id);
        //标题栏显示
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=(User) principal;
        model.addAttribute("out",user.getId());

        //显示点赞列表
        Page<Blog> blogPage =new Page<>(pageNum,pageSize);
        List<Blog> list = greatblogMapper.selectgreatblog(blogPage,id);
        model.addAttribute("lists",list);

        return "persongreat";
    }


    // 个人管理空间---- 传输所编辑的数据
    @ResponseBody
    @GetMapping("/getmyoneblog")
    public BlogVo getmyoneblog(int bid){
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq("id",bid);
        BlogVo vo = blogMapper.selectblogmsg(bid);
        return vo;
    }

    //更新
    @SneakyThrows
    @PostMapping("/updatebloguser")
    public String updateblog(BlogVo blogVo,
                             @RequestParam(value = "file") MultipartFile file){
        System.out.println("控制层执行更新方法");
        System.out.println("更新成功"+blogVo);

        //博客更改
        Blog blog =new Blog();
        blog.setId(blogVo.getId());
        blog.setTitle(blogVo.getTitle());
        blog.setContent(blogVo.getContent());
        blog.setView(blogVo.getView());
        blog.setClassifyid(blogVo.getClassifyid());
        blog.setUid(blogVo.getUid());
        blog.setClassifyid(blog.getClassifyid());

        //更改
        Blog byId = blogMapper.selectById(blogVo.getId());

        //图片
        System.out.println(file);
        //保存数据库的路径
        String sqlPath = null;
        //定义文件保存的本地路径
        String localPath = "G:\\imagehome\\";
        //定义 文件名
        String filename = null;
        //定义 返回信息
        String returnmsg ;

        if (!file.isEmpty()) {
            //生成uuid作为文件名称    随机生成的图片名字
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //获得文件类型（可以判断如果不是图片，禁止上传）
            String contentType = file.getContentType();
            //获得文件后缀名获取到jpg或者是其他格式
            String suffixName = contentType.substring(contentType.indexOf("/") + 1);  // mingzi.jpg 保留jpg
            //得到 文件名  一群随机数字加上他的格式 如xxxxx.jpg
            filename = uuid + "." + suffixName;
            System.out.println(filename);
            //文件保存路径  将图片保存在D盘的file中
            file.transferTo(new File(localPath + filename));
            sqlPath = "/image/" + filename;
            System.out.println("文件名字"+filename);
            System.out.println(sqlPath);
            byId.setPicture(sqlPath);
        } else {
            System.out.println("上传更改文件为空");
        }

        byId.setTitle(blogVo.getTitle());
        byId.setClassifyid(blogVo.getClassifyid());
        byId.setContent(blogVo.getContent());
        //把图片的相对路径保存至数据库
        blogservice.updateById(byId);

        //博客类型

        return "redirect:/topersonspace/"+blogVo.getUid();
    }

    //用户删除博客获取 并执行
    @GetMapping("/todelblogid")
    public String todelblogid(int delblogid,RedirectAttributes ra){
        //这个是博客id
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq("id",delblogid);
        System.out.println("nmd这是删除");
        //用于等等返回去的重定向
        int uid = blogMapper.selectOne(wrapper).getUid();
        int i = blogMapper.deleteById(delblogid);

        String ramsg;
        if (i==1){
            ramsg = "yes";
        }else{
            ramsg =null;
        }
        ra.addFlashAttribute("delblogmessage",ramsg);
        return "redirect:/topersonspace/"+uid;
    }

    //模糊查询 search页面
    @GetMapping("/tosearch")
    public String tosearch(Model model,String text,
                           @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "5")int pageSize){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=(User) principal;
        if (StringUtils.isNotBlank(text)){
            System.out.println("不为空");
        }else {
            System.out.println("为空");
            model.addAttribute("nullinput",0);
        }

//        System.out.println("输出text的值=》"+text);
        //显示搜索内容

        model.addAttribute("ads",user.getName());

        model.addAttribute("textcontent",text);

        //显示所搜索的博客
        //查找标题
        QueryWrapper<Blog> wrapper =new QueryWrapper();
        wrapper.like(StringUtils.isNotBlank(text),"title",text);
        IPage<Blog> page =new Page(pageNum,pageSize);
        List<Blog> records = blogservice.page(page, wrapper).getRecords();

        if (records.isEmpty()){
            model.addAttribute("nullblog",0);
            //0为输出空
        }else {
            model.addAttribute("blogs", records);
        }
        //计算总数，用于分页  标题版
        int count = blogservice.count(wrapper);
//        System.out.println("count=>"+count);

        float c = (float)count / (float)pageSize;
        double ceil = Math.ceil(c);
        int  a =(int)ceil;
        model.addAttribute("pagecount",a);
        //当前页数
        int currentpage =pageNum;
        model.addAttribute("currentpage",currentpage);



        //查找用户
        IPage<User> pageuser =new Page(pageNum,100);

        List<SearchUser> userList = blogservice.selectuser(text);
//        System.out.println("userlist=>"+userList);

        if (userList.isEmpty()){
            model.addAttribute("nulluser",0);
        }else {
            model.addAttribute("users",userList);
        }




        return "search";
    }


    //动态
    @GetMapping("/toaction")
    public  String toaction(Model model,
                            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
    @RequestParam(value = "pageSize", defaultValue = "5")int pageSize){

        //当前用户显示
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=(User) principal;
        IPage<BlogUser> page =new Page<>(pageNum,pageSize);
        //上面的id应该是用户的id
        List<BlogUser> list = blogMapper.actionbloguser(page,user.getId());
        //显示
        model.addAttribute("lists",list);

        //显示该关注人的总博客数
        int countactionbloguser = blogMapper.countactionbloguser(user.getId());
        System.out.println("关注的总数=>"+countactionbloguser);
        float c = (float)countactionbloguser / (float)pageSize;
        double ceil = Math.ceil(c);
        int  a =(int)ceil;
        model.addAttribute("pagecount",a);
        //当前页数
        int currentpage =pageNum;
        model.addAttribute("currentpage",currentpage);


        return "action";
    }



}
