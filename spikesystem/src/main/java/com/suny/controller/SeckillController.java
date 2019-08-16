package com.suny.controller;

import com.suny.dto.*;
import com.suny.entity.Seckill;
import com.suny.enumerate.SeckillStatEnum;
import com.suny.interfaces.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebParam;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/seckill")
public class SeckillController {
    private final SeckillService seckillService;

   @Autowired
    public SeckillController(SeckillService seckillService) {
        this.seckillService = seckillService;
    }

    /**
     * 进入秒杀列表
     * @param model 模型数据，存储秒杀商品信息
     * @retrun 秒杀列表详情页面
     */
    @RequestMapping(value = {"/list","","index"}, method = RequestMethod.GET)
    public String list(Model model){
        List<Seckill> seckillList = seckillService.getSeckillList();
        model.addAttribute("list",seckillList);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){

        if (seckillId == null){
            return "redirect:/seckill/list";
        }

        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null){
            return "forward:/seckill/list";
        }

        model.addAttribute(seckill);
        return "detail";
    }

    /**
     * 暴漏秒杀接口的方法
     * @param seckillId 秒杀商品Id
     * @return 根据不同的商品进行业务逻辑判断，返回不同的json实体
     */
    @RequestMapping(value = "/{seckillId}/exposer",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){

        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<>(true, exposer);
        }catch (Exception e){
            e.printStackTrace();
            result = new SeckillResult<>(false, e.getMessage());
        }
        return result;
    }

    /**
     * 用户执行秒杀，页面点击相应的秒杀链接，进入后获取对应的参数进行判断，返回对应的json结果
     * @param md5 j加密值
     * @param userphone 参与秒杀的用户手机号码，当作账号密码使用
     * @return 返回json数据，作为秒杀结果
     */
    @RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST)
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") long seckillId,@PathVariable("md5") String md5,@PathVariable(value = "userphone",required = false) Long userphone){
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
        if (userphone == null){
            return new SeckillResult<>(false,"没有注册");
        }
        try{
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId,userphone,md5);
            return new SeckillResult<>(true,seckillExecution);
        }catch (RepeatKillException e1){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
            return new SeckillResult<>(false,seckillExecution);
        }catch (SeckillCloseException e2){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillStatEnum.END);
            return new SeckillResult<>(false,seckillExecution);
        }catch (SeckillException e3){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillStatEnum.INNER_ERROR);
            return new SeckillResult<>(false,seckillExecution);
        }

        }

    /**
     * 获取服务器时间，防止用户串改客户端时间参与秒杀
     * @return 返回时间格式json数据
     */
    @RequestMapping(value = "time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<LocalDateTime> time(){
        LocalDateTime time = LocalDateTime.now();
        return new SeckillResult<>(true,time);
    }

}
