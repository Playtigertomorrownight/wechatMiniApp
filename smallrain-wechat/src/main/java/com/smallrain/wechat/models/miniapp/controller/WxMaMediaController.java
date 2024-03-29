package com.smallrain.wechat.models.miniapp.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.smallrain.wechat.models.miniapp.config.WechatMiniAppConfiguration;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.constant.WxMaConstants;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * <pre>
 *  小程序临时素材接口
 *  Created by BinaryWang on 2017/6/16.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/wx/media/{appid}")
public class WxMaMediaController {

  /**
   * 上传临时素材
   *
   * @return 素材的media_id列表，实际上如果有的话，只会有一个
   */
  @PostMapping("/upload")
  public List<String> uploadMedia(@PathVariable String appid, HttpServletRequest request) throws WxErrorException {
    final WxMaService wxService = WechatMiniAppConfiguration.getMaService(appid);
    CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
    if (!resolver.isMultipart(request)) {
      return Lists.newArrayList();
    }
    MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
    Iterator<String> it = multiRequest.getFileNames();
    List<String> result = Lists.newArrayList();
    while (it.hasNext()) {
      try {
        MultipartFile file = multiRequest.getFile(it.next());
        File newFile = new File(Files.createTempDir(), file.getOriginalFilename());
        log.info("filePath is ：" + newFile.toString());
        file.transferTo(newFile);
        WxMediaUploadResult uploadResult = wxService.getMediaService().uploadMedia(WxMaConstants.KefuMsgType.IMAGE,
            newFile);
        log.info("media_id ： " + uploadResult.getMediaId());
        result.add(uploadResult.getMediaId());
      } catch (IOException e) {
        log.error(e.getMessage(), e);
      }
    }
    return result;
  }

  /**
   * 下载临时素材
   */
  @GetMapping("/download/{mediaId}")
  public File getMedia(@PathVariable String appid, @PathVariable String mediaId) throws WxErrorException {
    final WxMaService wxService = WechatMiniAppConfiguration.getMaService(appid);
    return wxService.getMediaService().getMedia(mediaId);
  }
}
