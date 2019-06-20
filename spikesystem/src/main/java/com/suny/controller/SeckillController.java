//package com.suny.controller;
//
//import com.suny.entity.Seckill;
//import com.suny.interfaces.SeckillService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/seckill")
//public class SeckillController {
//    private final SeckillService seckillService;
//
//    public SeckillController(SeckillService seckillService) {
//        this.seckillService = seckillService;
//    }
//
//    /**
//     * 进入秒杀列表
//     * @param model 模型数据，存储秒杀商品信息
//     * @retrun 秒杀列表详情页面
//     */
//    @RequestMapping(value = {"/list","","index"}, method = RequestMethod.GET)
//    public String list(Model model){
//        List<Seckill> seckillList = seckillService.getSeckillList();
//        model.addAttribute("list",seckillList);
//        return "list";
//    }
//
//
//
//
//}
