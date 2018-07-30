package com.hyjf.am.config.service.impl.app;

import com.hyjf.am.config.controller.app.ShareNewsController;
import com.hyjf.am.config.dao.mapper.auto.InviteMapper;
import com.hyjf.am.config.dao.model.auto.Invite;
import com.hyjf.am.config.dao.model.auto.InviteExample;
import com.hyjf.am.config.service.app.ShareNewsService;
import com.hyjf.am.vo.market.ShareNewsBeanVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.ws.RespectBinding;
import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/27 09:23
 * @Description: ShareNewsServiceImpl
 */
@Service
public class ShareNewsServiceImpl implements ShareNewsService {
    private static final Logger logger = LoggerFactory.getLogger(ShareNewsServiceImpl.class);

    @Resource
    private InviteMapper inviteMapper;

    @Override
    public ShareNewsBeanVO queryShareNews() {
        InviteExample ie = new InviteExample();
        List<Invite> result= inviteMapper.selectByExample(ie);

        ShareNewsBeanVO shareNewsBean= new ShareNewsBeanVO();
        if(result!=null && result.size()>0){
            Invite invite= result.get(0);
            shareNewsBean.setTitle(invite.getTitle());
            shareNewsBean.setContent(invite.getContent());
            shareNewsBean.setImg(invite.getImg());
            shareNewsBean.setLinkUrl(invite.getLinkUrl());
        }
        return shareNewsBean;
    }
}
