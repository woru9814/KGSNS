package com.finalproject.sns.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.sns.service.SnsService;
import com.finalproject.sns.service.dao.SnsDAO;
import com.finalproject.sns.vo.CommentVO;
import com.finalproject.sns.vo.FriendVO;
import com.finalproject.sns.vo.GoodBadVO;
import com.finalproject.sns.vo.GroupVO;
import com.finalproject.sns.vo.MemVO;
import com.finalproject.sns.vo.MsgVO;
import com.finalproject.sns.vo.SearchajaxVo;
import com.finalproject.sns.vo.TextVO;

@Service("snsService")
public class SnsServiceImpl implements SnsService {

	@Autowired
	private SnsDAO snsDao;

	@Override
	public void insertMem(MemVO memVO) throws SQLException {
		snsDao.insertMem(memVO);
	}

	@Override
	public void logoutmem(MemVO memVO) {
		snsDao.logoutmem(memVO);
	}

	@Override
	public void loginmem(MemVO memVO) {
		snsDao.loginmem(memVO);
	}

	@Override
	public void upSns_frd(Map<String, Object> params) {
		snsDao.upSns_frd(params);
	}
	@Override
	public void upSns_Msg(MsgVO msgVO) {
		snsDao.upSns_Msg(msgVO);
	}
	
	@Override
	public void updateMem(MemVO memVO) {
		snsDao.updateMem(memVO);
	}

	@Override
	public void deleteMem(MemVO memVO) {
		snsDao.deleteMem(memVO);
	}

	@Override
	public void insertText(TextVO textVO) {
		snsDao.insertText(textVO);
	}
	
	@Override
	public void insertImg(TextVO textVO) {
		snsDao.insertImg(textVO);
	}

	@Override
	public MemVO login(MemVO memVO) {
		MemVO result = snsDao.login(memVO);
		if (result != null) {
			return result;
		} else {
			return null;
		}

	}

	@Override
	public MemVO getOne(MemVO memVO) {
		MemVO result = snsDao.getOne(memVO);
		if(result != null)
			return result;
		else
			return null;
	}
	
	@Override
	public MemVO getOne2(MemVO memVO) {
		MemVO result = snsDao.getOne2(memVO);
		if(result != null)
			return result;
		else
			return null;
	}

	@Override
	public ArrayList<FriendVO> getFriend(MemVO memVO) {
		return snsDao.getFriend(memVO);
	}

	@Override
	public MemVO updateProfile(MemVO memVO) {
		snsDao.updateProfile(memVO);
		return updateVO(memVO);
	}

	@Override
	public MemVO updateVO(MemVO memVO) {
		return snsDao.updateVO(memVO);
	}

	@Override
	public ArrayList<MsgVO> getAllMSG(MsgVO msgVO) {
		return snsDao.getAllMSG(msgVO);
	}
	
	@Override
	public MsgVO getMSG(MsgVO msgVO) {
		return snsDao.getMSG(msgVO);
	}
	
	@Override
	public ArrayList<MemVO> searchUser(MemVO memVO) {
		System.out.println("서비스임플 진입.");
		return snsDao.searchUser(memVO);
	}
	
	@Override
	public ArrayList<TextVO> searchtxt(String str) {
		return snsDao.searchtxt(str);
	}

	@Override
	public String searchId(MemVO memVO) {
		return snsDao.searchId(memVO);
	}

	public void addFriend(Map<String, Object> params) throws Exception {
		snsDao.addFriend(params);
	}
	
	@Override
	public void AddSNS_Msg(MsgVO msgVO) {
		snsDao.AddSNS_Msg(msgVO);
	}
	
	@Override
	public void AddSNS_Msg2(MsgVO msgVO) {
		snsDao.AddSNS_Msg2(msgVO);
	}

	@Override
	public int searchEmail(String email) {

		return snsDao.searchEmail(email);
	}

	@Override
	public String searchPw(MemVO memVO) {

		return snsDao.searchPw(memVO);
	}

	@Override
	public String getFeedCount() {
		String result = null;
		try {
			result = snsDao.getFeedCount();
		} catch (Exception e) {

			result = "1";
		}

		return result;
	}

	@Override
	public void addGroup(GroupVO groupVO) {
		snsDao.addGroup(groupVO);
	}
	
	@Override
	public SearchajaxVo searchajax(MemVO memVO) {
		return snsDao.searchajax(memVO);
	}
	
	@Override
	public TextVO getFeed(String TXT_NUM) {
		
		return snsDao.getFeed(TXT_NUM);
	}
	
	@Override
	public ArrayList<CommentVO> getComments(CommentVO commentVO) {
		
		return snsDao.getComments(commentVO);
	}

	@Override
	public ArrayList<GoodBadVO> getGoodBad(GoodBadVO goodBadVO) {
		
		return snsDao.getGoodBad(goodBadVO);
	}

	@Override
	public void goodInsertPlus(GoodBadVO goodBadVO) {
		snsDao.goodInsertPlus(goodBadVO);
	}
	
	@Override
	public void badInsertPlus(GoodBadVO goodBadVO) {
		snsDao.badInsertPlus(goodBadVO);
	}
	
	@Override
	public void deleteGoodBad(GoodBadVO goodBadVO) {
		snsDao.deleteGoodBad(goodBadVO);
	}

	@Override
	public int getLikeCount(GoodBadVO goodBadVO) {
		return snsDao.getLikeCount(goodBadVO);
	}

	@Override
	public int getHateCount(GoodBadVO goodBadVO) {
		return snsDao.getHateCount(goodBadVO);
	}

	@Override
	public void updateGoodBad(GoodBadVO goodBadVO) {
		snsDao.updateGoodBad(goodBadVO);
	}

	@Override
	public void addComment(CommentVO commentVO) {
		snsDao.addComment(commentVO);
	}

	@Override
	public ArrayList<TextVO> friendsFeed(MemVO memVO) {
		
		return snsDao.friendsFeed(memVO);
	}

	@Override
	public ArrayList<TextVO> getMyFeeds(String id) {
		
		return snsDao.getMyFeeds(id);
	}

	@Override
	public ArrayList<Integer> getTXT_NUM(String id) {
		
		return snsDao.getTXT_NUM(id);
	}
	
	@Override
	public ArrayList<Integer> getTXT(String id) {
		
		return snsDao.getTXT(id);
	}


}
