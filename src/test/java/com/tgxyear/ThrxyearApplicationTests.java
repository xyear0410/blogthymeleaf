package com.tgxyear;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tgxyear.mapper.*;
import com.tgxyear.pojo.*;
import com.tgxyear.service.*;
import com.tgxyear.service.impl.BclassifylinkServiceimpl;
import com.tgxyear.utils.BlogUser;
import com.tgxyear.utils.Commentvo;
import com.tgxyear.utils.SearchUser;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest
class ThrxyearApplicationTests {

    @Autowired
    Blogservice blogservice;

    @Autowired
    UBlinkMapper uBlinkMapper;

    @Test
    void contextLoads() {
        QueryWrapper wrapper =new QueryWrapper();
//        wrapper.inSql("uid","select uid from blog");
        List list = blogservice.list(wrapper);
        list.forEach(System.out::println);
    }

    @Test
    void contextLoad1s() {
        List<UBlink> getalls = uBlinkMapper.getalls();
        getalls.forEach(System.out::println);
    }

    @Autowired
    UBlinkservice uBlinkservice;
    @Test
    void contextLoad2s() {
        List<UBlink> getalls = uBlinkservice.getalls();
        getalls.forEach(System.out::println);
    }

    @Autowired
    UserMapper userMapper;

    @Autowired
    BlogMapper blogMapper;
    @Test
    void contextLoad3s() {
        Blog blog =new Blog();
        blog.setTitle("not give dad");
        blog.setContent("sdadaczcx");
        blog.setPicture("czjci");

        int i = blogMapper.insert(blog);



    }
    @Test
    void contextLoad4s() {
          int id=1;
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq("id",id);
//        List list = blogservice.list(wrapper);
        Blog one = blogservice.getOne(wrapper);
        System.out.println(one);
    }

    @Test
    void contextLoad5s() {
    QueryWrapper wrapper =new QueryWrapper();
        List<User> selectList = uBlinkservice.getalluser();
        selectList.forEach(System.out::println);
    }

@Autowired
    URservice uRservice;

    @Test
    void contextLoad6s() {
            int uid =1;
        int adduserroid = uRservice.adduserroid(uid);
        System.out.println("改变了"+adduserroid+"行");
    }

    @Test
    void contextLoad7s() {
        int id =1;
        String findima = blogMapper.findima(id);
        System.out.println(findima);
    }


    @Autowired
    CommentMapper commentMapper;
    @Test
    void contextLoad8s() {
        List<Comment> comments = commentMapper.selectList(null);
        comments.forEach(System.out::println);
    }



    @Autowired
    Commentservice commentservice;
    @Test
    void contextLoad9s() {
        List<Comment> list = commentservice.list();
        list.forEach(System.out::println);
    }


    @Test
    void contextLoad10s() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",1);
        Blog one = blogservice.getOne(wrapper);
        System.out.println(one);
    }

    @Test
    void contextLoad11s() {
        int id =1;
        QueryWrapper wrapper1 =new QueryWrapper();
        wrapper1.eq("uid",id);
        int i = uBlinkservice.getuID(id);
        System.out.println("用户id为"+i);
        String username = userMapper.getUsername(i);
        System.out.println(username);
    }


    @Test
    void contextLoad12s() {
        List<Comment> commentList = commentMapper.getblogonecomment(1);
        commentList.forEach(System.out::println);
    }


    @Test
    void contextLoad13s() {
        int i =1;
        List<User> getcommentname = userMapper.getcommentname(i);
        getcommentname.forEach(System.out::println);

    }
    @Test
    void contextLoad14s() {
        int count = commentservice.count();
        System.out.println("output"+count);


    }
    @Test
    void contextLoad15s() {
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq("parent_comment_id",-1);
        List listcomment = commentMapper.selectList(wrapper);
        listcomment.forEach(System.out::println);

    }
    @Test
    void contextLoad16s() {
        int i = uBlinkservice.getuID(2);
        System.out.println("输出的值"+i);

    }


    @Test
    void contextLoad17s() {
        List<Comment> list = commentMapper.findByParentIdNull(-1);
      list.forEach(System.out::println);
    }



    @Test
    void contextLoad18s() {
        List<Comment> findtwo = commentMapper.findtwo(1, -1);
        findtwo.forEach(System.out::println);
    }

    @Test
    void contextLoad19s() {
        List<UBlink> list = uBlinkservice.getalls();
        list.forEach(System.out::println);
    }

    @Test
    void contextLoad20s() {
        Comment comment = new Comment();
        comment.setBid(1);
        int i = commentMapper.saveComment(comment);
        System.out.println(i);
    }

    @Test
    void  test1(){
        String timeStr1= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("当前时间"+timeStr1);

        Date date = strToDateLong(timeStr1);
        System.out.println("时间"+new Date());
        Calendar c =Calendar.getInstance();
        System.out.println("c的值:"+c);
    }
    public static Date strToDateLong(String strDate) {
 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 ParsePosition pos = new ParsePosition(0);
 Date strtodate = formatter.parse(strDate, pos);
return strtodate;
    }

    public static String getStringDateShort() {
Date currentTime = new Date();
SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
String dateString = formatter.format(currentTime);
 return dateString;
    }


    @Autowired
    ClassifyMapper classifyMapper;
    @Test
    void  test02(){
        List<Classify> list = classifyMapper.selectList(null);
        list.forEach(System.out::println);
    }

    @Autowired
    BclassifylinkMapper bc ;

    @Test
    void  test03(){
        List<B_clink> clinks = bc.selectList(null);
        clinks.forEach(System.out::println);
    }

    @Test
    void  test04(){
            List<UBlink> getalls = uBlinkMapper.getalls();
            //遍历
            for (UBlink getall : getalls){
                //获取博客id
                int bid = getall.getBid();
                System.out.println("这是第"+bid+"个博客");
            }
        System.out.println(getalls);
    }

    @Test
    void  test05(){
        //你现在获取到了bid
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq("bid",1);
        bc.selectList(wrapper).forEach(System.out::println);
    }

    @Autowired
    BclassifylinkService bcService;

    @Test
    void  test06(){
        //博客对应的分类应该是一对多
        List<Classify> clinks = bc.getblogclassifyid(1);
        for (Classify clink : clinks) {

        }
    }

    @Test
    void test07(){
        List<Classify> list = classifyMapper.selectList(null);
        list.forEach(System.out::println);
    }

    @Test
    void test08(){
         int id=1;
        Classify classify = classifyMapper.selectById(id);
        System.out.println(classify);
    }

    @Test
    void  test09(){
        int id =1;
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq("bid",id);
        Integer count = bc.selectCount(wrapper);
        System.out.println(count);
    }

    @Autowired
    GreatblogMapper greatblogMapper;

    @Test
    void test10(){
        List<Greatblog> greatblogs = greatblogMapper.selectList(null);
        System.out.println(greatblogs);
    }

    @Test
    void test11(){
        System.out.println(greatblogMapper.selectblogonegreat(1));
    }

    @Test
    void test12(){
        Page<Blog> userPage = new Page<>(1, 2);
        userPage =blogMapper.selectPage(userPage,null);
        List<Blog> records = userPage.getRecords();
        records.forEach(System.out::println);
    }

    @Test
    void test13(){
        List<UBlink> blinks = uBlinkservice.getalls();
        blinks.forEach(System.out::println);
        System.out.println("============================================");
        List<Blog> blogs = blogMapper.selectList(null);
        System.out.println(blogs);
    }


    @Test
    void TEST14(){
        QueryWrapper<Blog> wrapper =new QueryWrapper();
        Integer integer = blogMapper.selectCount(wrapper);
        System.out.println("总数"+integer);
    }
    @Autowired
    URMapper urMapper;
    @Test
    void TEST15() {
        Page<User_role> userPage =new Page<>(1,2);
        userPage = urMapper.selectPage(userPage, null);
        List<User_role> records = userPage.getRecords();
        records.forEach(System.out::println);
        QueryWrapper<User> wrapper =new QueryWrapper();
    }

    @Test
    void TEST16() {
        Page<User> userPage =new Page<>(1,2);
        IPage<User> userIPage = urMapper.selectPageVo(userPage);
        List<User> records = userIPage.getRecords();
        System.out.println(records);
    }

    @Test
    void TEST17() {
        int selectcountid = urMapper.selectcountid();
        System.out.println("dsa"+selectcountid);
    }
    @Test
    void TEST18() {
        Page<Blog> userPage =new Page<>(1,2);
        IPage<Blog> blogPage = blogMapper.selectPageText(userPage);
        List<Blog> records = blogPage.getRecords();
        records.forEach(System.out::println);
    }

    @Test
    void TEST19() {
        int i = bcService.count();
        System.out.println("所取得"+i);
    }

    @Test
    void test20(){
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq("bid",1);
       int a = bc.selectCount(wrapper);
        System.out.println(a);
    }

    @Test
    void test21(){
        boolean b = uBlinkMapper.insertub(1, 1);
        System.out.println(b);
    }

    @Test
    void test22() {
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq("title",2021);


        Blog blog = blogMapper.selectOne(wrapper);
        System.out.println(blog);
    }
    @Test
    void  test23(){
        int i = blogMapper.getmaxBid();
        System.out.println(i);
    }
    @Test
    void  test24(){
        System.out.println(userMapper.selectoneuser(1));
    }
    @Test
    void  test25() {
        User user =new User();
        user.setId(11);
        user.setName("开");
        user.setPassword("321");
        user.setSex(1);
        user.setEmail("473902@111.com");
        boolean b = userMapper.updateuser(user);
        System.out.println(b);
    }
    @Test
    void  test26() {
        BlogVo vo = blogMapper.selectblogmsg(1);
        System.out.println(vo);
    }

    @Test
    void  test27() {
        System.out.println(bc.selectCount(null));
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq("classifyid",1);
        System.out.println(bc.selectCount(wrapper));
    }
    @Test
    void  test28() {
        List<Classify> classifies = classifyMapper.selectList(null);
        String s = JSON.toJSONString(classifies);
        System.out.println(s);
    }

    @Test
    void  test29() {
        int id =1;
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("uid", id);
        List<Blog> lists = blogservice.list(wrapper);

        System.out.println(lists);
    }

    @Autowired
    FollowMapper followMapper;
    @Test
    void  test30() {
        System.out.println(followMapper.selectList(null));
    }



@Autowired
UpicMapper upicMapper;
    @Test
    void  test31() {
       Upic upic =new Upic();
       upic.setId(1);
       upic.setUid(1);
       upic.setPic("111");
        List<Upic> upics = upicMapper.selectList(null);
        System.out.println(upics);
    }
    @Test
    void  test32() {
        Upic upic = upicMapper.selectById(2);
        System.out.println(upic);


    }

    @Test
    void  test33() {
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq("id",1);
        List<Blog> list = blogservice.list(wrapper);
        list.forEach(System.out::println);
    }

    @Test
    void  test34() {
//        IPage<User> userIPage =new Page<>(1,2);
//
//        List<User> u = userMapper.selectadpageuser(userIPage,
//                null, "x");
        int x = userMapper.countadpageuser(null, "x");
        //        QueryWrapper<User> wrapper =new QueryWrapper();
//        wrapper.like(StringUtils.isNotBlank("x"),"name","x");
//        List<User> list = userMapper.selectList(wrapper);
//        list.forEach(System.out::println);
//        u.forEach(System.out::println);
        System.out.println(x);
    }

    @Test
    void  test35() {
        String name ="1";
        List<Blog> list = blogMapper.selectadpageblog(null, "20", null);
        System.out.println("1111");
        list.forEach(System.out::println);

    }
    @Test
    void  test36() {

        int one = greatblogMapper.checkgreat(8, 1);
        System.out.println("one=>"+one);
    QueryWrapper<Greatblog> queryWrapper =new QueryWrapper<>();
    queryWrapper.eq("uid",1)
                .eq("bid",8);
        System.out.println(greatblogMapper.selectOne(queryWrapper));
    }
    @Test
    void  test37() {
        IPage<Comment> page =new Page<>(1,5);
        List<Comment> list = commentMapper.findallcomment(page);
        list.forEach(System.out::println);
    }
    @Test
    void  test38() {
        Commentvo oneById = commentMapper.findOneById(24);
        System.out.println("oneByud=>"+oneById);
    }

    @Test
    void  test39() {
        System.out.println(userMapper.getuserphoto(1));
    }


    @Test
    void  test40() {
        blogMapper.actionblog(1).forEach(System.out::println);
    }
    @Test
    void  test41() {
        IPage<BlogUser> page =new Page<>(1,3);
        blogMapper.actionbloguser(page,1).forEach(System.out::println);
    }
    @Test
    void  test42() {
            String a ="  1";
        System.out.println("值"+a);
            if (StringUtils.isNotBlank(a.trim())){
                System.out.println("a=>"+a);
                System.out.println("不为空");
            }else {
                System.out.println("为空");
            }
    }

    @Test
    void  test43(){
        List<SearchUser> searchuserlike = blogMapper.searchuserlike("x");
//        for (int i = 0; i < searchuserlike.size(); i++) {
//            searchuserlike.get(i).getId();//user的id
//            //查询粉丝数
//            QueryWrapper<Follow> wrapper = new QueryWrapper<>();
//            wrapper.eq("noticeid",searchuserlike.get(i).getId());
//            followMapper.selectCount(wrapper);
//            searchuserlike.get(i).setAllnoticenum(followMapper.selectCount(wrapper));
//            System.out.println("zong=>"+followMapper.selectCount(wrapper));
//            //查询点赞数
//            QueryWrapper<Greatblog> wrapper1 =new QueryWrapper<>();
//            wrapper1.eq("uid",searchuserlike.get(i).getId());
//            greatblogMapper.selectCount(wrapper1);
//            searchuserlike.get(i).setAllgreat(greatblogMapper.selectCount(wrapper1));
//            System.out.println(searchuserlike.get(i));
//
//            //查询关注数
//
//        }

    }
    @Test
    void  test44(){
        System.out.println(greatblogMapper.getusergreat(4));
    }

    @Test
    void test46(){
        String k = classifyMapper.checkclassifyname("k");
        if (k ==null){
            System.out.println("weikong ");
        }else {
            System.out.println("buweik");
        }
    }

    @Autowired
    Labelmapper labelmapper;
    @Autowired
    Labelidmapper labelidmapper;
    @Test
    void test47(){
        System.out.println(labelmapper.selectList(null));
        System.out.println("=====");
        System.out.println(labelidmapper.selectList(null));
    }

    @Autowired
    LabelViewMapper labelViewMapper;

    @Test
    void test48() {
        Random random  =new Random();
        int randNum =random.nextInt(2);
        System.out.println(randNum);
    }
}


