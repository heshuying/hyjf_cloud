package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.WhereaboutsPagePicture;
import com.hyjf.am.user.dao.model.auto.WhereaboutsPagePictureExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WhereaboutsPagePictureMapper {
    int countByExample(WhereaboutsPagePictureExample example);

    int deleteByExample(WhereaboutsPagePictureExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WhereaboutsPagePicture record);

    int insertSelective(WhereaboutsPagePicture record);

    List<WhereaboutsPagePicture> selectByExample(WhereaboutsPagePictureExample example);

    WhereaboutsPagePicture selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WhereaboutsPagePicture record, @Param("example") WhereaboutsPagePictureExample example);

    int updateByExample(@Param("record") WhereaboutsPagePicture record, @Param("example") WhereaboutsPagePictureExample example);

    int updateByPrimaryKeySelective(WhereaboutsPagePicture record);

    int updateByPrimaryKey(WhereaboutsPagePicture record);
}