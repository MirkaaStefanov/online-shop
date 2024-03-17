package com.example.activitiesplatform.Mapper;

import com.example.activitiesplatform.ActivitiesPlatformApplication;
import com.example.activitiesplatform.Activity.Activity;
import com.example.activitiesplatform.Dto.ActivityDto;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {

    public Activity toEntity(ActivityDto activityDto) {
        Activity activity = new Activity();
        activity.setName(activityDto.getName());
        activity.setCategory(activityDto.getCategory());
        activity.setPrice(activityDto.getPrice());
        activity.setDescription(activityDto.getDescription());
        activity.setPlace(activity.getPlace());
        activity.setAppropriateAge(activityDto.getAppropriateAge());
        activity.setDuration(activityDto.getEndDate().getTime() - activityDto.getStartDate().getTime());
        activity.setPicture(activityDto.getPicture());

        return activity;
    }
}
