package com.debugers.alltv.controller;

import com.debugers.alltv.model.LiveRoom;
import com.debugers.alltv.result.Result;
import com.debugers.alltv.service.BilibiliService;
import com.debugers.alltv.service.DouYuService;
import com.debugers.alltv.service.EGameService;
import com.debugers.alltv.service.HuYaService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Api(tags = "综合各个平台")
@RestController
@RequestMapping("api/top")
public class TopController {
    private final DouYuService douYuService;
    private final BilibiliService bilibiliService;
    private final HuYaService huYaService;
    private final EGameService eGameService;

    public TopController(DouYuService douYuService, BilibiliService bilibiliService, HuYaService huYaService, EGameService eGameService) {
        this.douYuService = douYuService;
        this.bilibiliService = bilibiliService;
        this.huYaService = huYaService;
        this.eGameService = eGameService;
    }
    @GetMapping("live/{cid}")
    public Result<List<LiveRoom>> getTopRooms(@PathVariable("cid") String cid, @RequestParam(defaultValue = "1") Integer pageNum){
        List<LiveRoom> rooms = douYuService.getTopRoomsByCid(cid, 20, pageNum);
        //暂时不知道怎么映射 各个平台的分类关系
        if ("0".equals(cid)){
            List<LiveRoom> topRooms = bilibiliService.getTopRooms(0, 10, pageNum);
            List<LiveRoom> topRoomsHuya = huYaService.getTopRooms(pageNum,10);
            List<LiveRoom> topRoomsEGame = eGameService.getTopRooms(pageNum,10);
            rooms.addAll(topRooms);
            rooms.addAll(topRoomsHuya);
            rooms.addAll(topRoomsEGame);
        }
        Collections.sort(rooms);
        return Result.success(rooms);
    }
}
