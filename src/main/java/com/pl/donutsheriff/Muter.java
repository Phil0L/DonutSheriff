package com.pl.donutsheriff;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class Muter extends ListenerAdapter {

    private final HashMap<Member, Role[]> savedRoles;

    private static final String ROLE_ID = "705439859547963422";
    private static final String VOICE_CHANNEL_ID = "691625665174437919";

    public Muter() {
        this.savedRoles = new HashMap<>();
    }

    @Override
    public void onGuildMemberRoleAdd(@Nonnull GuildMemberRoleAddEvent event) {
        Guild guild = event.getGuild();
        Role addedRole = event.getRoles().get(0);
        if (!addedRole.getId().equals(ROLE_ID))
            return;
        Member member = event.getMember();
        if (member.isOwner())
            return;
        Role[] memberRoles = member.getRoles().toArray(new Role[]{});
        savedRoles.remove(member);
        savedRoles.put(member, memberRoles);
        guild.modifyMemberRoles(member, guild.getRoleById(ROLE_ID)).queue();

        try {
            guild.moveVoiceMember(member, guild.getVoiceChannelById(VOICE_CHANNEL_ID)).queue();
        }catch (IllegalStateException ignored){}

    }

    @Override
    public void onGuildMemberRoleRemove(@Nonnull GuildMemberRoleRemoveEvent event) {
        Guild guild = event.getGuild();
        Role addedRole = event.getRoles().get(0);
        if (!addedRole.getId().equals(ROLE_ID))
            return;
        Member member = event.getMember();
        if (member.isOwner())
            return;

        if (savedRoles.containsKey(member)){
            Role[] memberRoles = savedRoles.get(member);
            guild.modifyMemberRoles(member, memberRoles).queue();
            savedRoles.remove(member);
        }
    }

    @Override
    public void onGuildVoiceMove(@Nonnull GuildVoiceMoveEvent event) {
        Guild guild = event.getGuild();
        VoiceChannel channel = event.getChannelJoined();
        if (channel != guild.getVoiceChannelById(VOICE_CHANNEL_ID))
            return;
        Member member = event.getMember();
        if (member.isOwner())
            return;
        Role[] memberRoles = member.getRoles().toArray(new Role[]{});
        savedRoles.remove(member);
        savedRoles.put(member, memberRoles);
        guild.modifyMemberRoles(member, guild.getRoleById(ROLE_ID)).queue();
    }
}
