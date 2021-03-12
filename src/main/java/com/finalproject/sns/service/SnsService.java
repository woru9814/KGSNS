package com.finalproject.sns.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import com.finalproject.sns.vo.CommentVO;
import com.finalproject.sns.vo.FriendVO;
import com.finalproject.sns.vo.GoodBadVO;
import com.finalproject.sns.vo.GroupVO;
import com.finalproject.sns.vo.MemVO;
import com.finalproject.sns.vo.MsgVO;
import com.finalproject.sns.vo.SearchajaxVo;
import com.finalproject.sns.vo.TextVO;

public interface SnsService {
	
void insertMem(MemVO memVO) throws SQLException;
	
	void logoutmem(MemVO memVO);
	
	void loginmem(MemVO memVO);
	
	void updateMem(MemVO memVO);
	
	void deleteMem(MemVO memVO);

	void insertText(TextVO textVO);

	MemVO login(MemVO memVO);

	MemVO getOne(MemVO memVO);
	
	MemVO getOne2(MemVO memVO);

	ArrayList<FriendVO> getFriend(MemVO memVO) throws Exception;

	MemVO updateProfile(MemVO memVO);
	
	MemVO updateVO(MemVO memVO);

	ArrayList<MemVO> searchUser(MemVO memVO) throws Exception;
	
	ArrayList<TextVO> searchtxt(String str) throws Exception;

	String searchId(MemVO memVO);

	void addFriend(Map<String, Object> params) throws Exception;

	int searchEmail(String parameter);

	String searchPw(MemVO memVO);

	String getFeedCount();
	
	ArrayList<MsgVO> getAllMSG(MsgVO msgVO);
	
	MsgVO getMSG(MsgVO msgVO);
	
	void AddSNS_Msg(MsgVO msgVO);
	
	void AddSNS_Msg2(MsgVO msgVO);
	
	void upSns_frd(Map<String, Object> params);
	
	void upSns_Msg(MsgVO msgVO);

	void insertImg(TextVO textVO);

	void addGroup(GroupVO groupVO);
	
	SearchajaxVo searchajax(MemVO memVO);
	
	TextVO getFeed(String TXT_NUM);

	void addComment(CommentVO commentVO);

	ArrayList<CommentVO> getComments(CommentVO commentVO);

	ArrayList<GoodBadVO> getGoodBad(GoodBadVO goodBadVO);
	
	void goodInsertPlus(GoodBadVO goodBadVO);
	
	void badInsertPlus(GoodBadVO goodBadVO);
	
	void deleteGoodBad(GoodBadVO goodBadVO);
	
	int getLikeCount(GoodBadVO goodBadVO);
	
	int getHateCount(GoodBadVO goodBadVO);

	void updateGoodBad(GoodBadVO goodBadVO);

	ArrayList<TextVO> friendsFeed(MemVO memVO);

	ArrayList<TextVO> getMyFeeds(String id);

	ArrayList<Integer> getTXT_NUM(String id);
	
	ArrayList<Integer> getTXT(String id);
}
