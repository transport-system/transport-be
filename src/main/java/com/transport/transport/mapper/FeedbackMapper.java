package com.transport.transport.mapper;

import com.transport.transport.model.entity.FeedBack;
import com.transport.transport.model.response.feeedback.FeedbackResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", config = ConfigurationMapper.class)
public interface FeedbackMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "account.username", target = "name")
    @Mapping(source = "createTime", target = "date")
    @Mapping(source = "detail", target = "detail")
    @Mapping(source = "ratingScore", target = "rating")
    @Mapping(source = "id", target = "feedBackId")
    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.avatarImage", target = "avatar")
    FeedbackResponse mapFeedbackResponseFromFeedback(FeedBack feedBack);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "mapFeedbackResponseFromFeedback")
    public List<FeedbackResponse> mapToFeedbackResponse(List<FeedBack> feedBack);
}
