

package com.ontopad.oauth.facebook;

/**
 *
 * @author admin
 */
public enum FacebookPermission {

    id,
    name,
    first_name,
    last_name,
    link,
    username,
    gender,
    locale,
    //
    read_friendlists,
    read_insights,
    read_mailbox,
    read_requests,
    read_stream,
    xmpp_login,
    ads_management,
    create_event,
    manage_friendlists,
    manage_notifications,
    user_online_presence,
    friends_online_presence,
    publish_checkins,
    publish_stream,
    rsvp_event,
    //
    publish_actions,
    user_actions_music("user_actions.music"),
    user_actions_news("puser_actions.news"),
    user_actions_video("user_actions.video"),
    user_actions_APP_NAMESPACE("user_actions:APP_NAMESPACE"),
    user_games_activity,
    friends_actions_music("friends_actions.music"),
    friends_actions_news("friends_actions.news"),
    friends_actions_video("friends_actions.video"),
    friends_actions_APP_NAMESPACE("friends_actions:APP_NAMESPACE"),
    friends_games_activity,
    //
    manage_pages,
    //
    user_about_me,
    user_activities,
    user_birthday,
    user_checkins,
    user_education_history,
    user_events,
    user_groups,
    user_hometown,
    user_interests,
    user_lines,
    user_location,
    user_notes,
    user_photos,
    user_questions,
    user_relationships,
    user_relationships_details,
    user_religion_politics,
    user_status,
    user_subscriptions,
    user_videos,
    user_website,
    user_work_history,
    email,
    friends_about_me,
    friends_activities,
    friends_birthday,
    friends_checkins,
    friends_education_history,
    friends_events,
    friends_groups,
    friends_hometown,
    friends_interests,
    friends_lines,
    friends_location,
    friends_notes,
    friends_photos,
    friends_questions,
    friends_relationships,
    friends_relationships_details,
    friends_religion_politics,
    friends_status,
    friends_subscriptions,
    friends_videos,
    friends_website,
    friends_work_history;
    
    private String enumName;

    private FacebookPermission(String enumName) {
        this.enumName = enumName;
    }

    @Override
    public String toString() {
        return enumName;
    }
}
