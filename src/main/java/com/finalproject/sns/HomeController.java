package com.finalproject.sns;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.finalproject.sns.service.SnsService;
import com.finalproject.sns.vo.CommentVO;
import com.finalproject.sns.vo.FriendVO;
import com.finalproject.sns.vo.GoodBadVO;
import com.finalproject.sns.vo.GroupVO;
import com.finalproject.sns.vo.MemVO;
import com.finalproject.sns.vo.MsgVO;
import com.finalproject.sns.vo.SearchajaxVo;
import com.finalproject.sns.vo.TextVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Resource(name = "snsService")
	private SnsService snsService;
	@Autowired
	private JavaMailSender mailSender;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	ArrayList<FriendVO> arr = null;
	ArrayList<TextVO> friendsFeed = null;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	/*	@RequestMapping(value = "/", method = RequestMethod.GET)
		public String home(Locale locale, Model model) {
			logger.info("Welcome home! The client locale is {}.", locale);
	
			Date date = new Date();
			DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
	
			String formattedDate = dateFormat.format(date);
	
			model.addAttribute("serverTime", formattedDate);
	
			return "home";
		}*/
/*
	@RequestMapping(value = "/searchtxt.do")
	public String searchtxt(@ModelAttribute("memVO") MemVO memVO, Model model) throws Exception {
		ArrayList<TextVO> arr = snsService.searchtxt(memVO);
		model.addAttribute("alist", arr);
		
		return "searchtxt";
	}
*/	
	@RequestMapping(value = "/searchUser.do")
	public String searchUser(@ModelAttribute("memVO") MemVO memVO, Model model) throws Exception {
		ArrayList<MemVO> arr = snsService.searchUser(memVO);
		model.addAttribute("alist", arr);
		return "ShowUser";
	}
	@RequestMapping(value="/newGroup.do")
	public String newGroupForm(@ModelAttribute("memVO") MemVO memVO, Model model) {
		return "newGroup";
	}
	@RequestMapping(value = "/updateProfile.do")
	public String updateProfile(MemVO memVO, MultipartFile file, HttpServletRequest request, Model model) {

		if (!file.isEmpty()) {
			String root_path = request.getSession().getServletContext().getRealPath("/");
			String attach_path = "/resources/img/profile/";
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			String memimg = memVO.getId() + "." + extension;
			memVO.setMemimg(memimg);
			File file2 = new File(root_path + attach_path + memimg);
			try {
				file.transferTo(file2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			MemVO updateVO = snsService.updateProfile(memVO);
			model.addAttribute("loginVO", updateVO);
		}

		return "setSession";
	}

	@RequestMapping(value = "/UserUpdateConfirm.do")
	public String userUpdateConfirm(@ModelAttribute("memVO") MemVO memVO, Model model) {

		snsService.updateMem(memVO);

		MemVO loginVO = snsService.login(memVO);
		model.addAttribute("loginVO", loginVO);
		return "setSession";
	}

	@RequestMapping(value = "/userDelete.do")
	public String userDelete(@ModelAttribute("memVO") MemVO memVO, Model model) {
		snsService.deleteMem(memVO);
		return "deleteSession";
	}

	@RequestMapping(value = "/txtInsert.do")
	public String textInsert(@ModelAttribute("textVO") TextVO textVO, Model model) {
		snsService.insertText(textVO);
		return "myPage";
	}

	@RequestMapping(value = "/searchIdForm.do")
	public String searchIdForm() {
		return "searchId";
	}
	@RequestMapping(value = "/searchPwForm.do")
	public String searchPwForm() {
		return "searchpw";
	}
	
	@RequestMapping(value = "/searchId.do")
	public String searchId(MemVO memVO, Model model) {
		String id = snsService.searchId(memVO);
		if(id != null) {
			model.addAttribute("id", id);
		}
		return "searchidresult";
	}
	
	@RequestMapping(value ="/searchpw.do")
	public String searchPw(MemVO memVO, Model model) {
		String pw = snsService.searchPw(memVO);
		if(pw != null) {
			model.addAttribute("pw", pw);
		}
		return "passwordresult";
	}
	



	

	@ResponseBody
	@RequestMapping(value = "/searchEmail.do")
	public String searchEmail(HttpServletRequest request) {
		if (snsService.searchEmail(request.getParameter("e_mail")) == 0) {
			return "none";
		}
		return "success";
	}

	@RequestMapping(value = "/addfriendDB.do")
	public String addfriendDB(MsgVO MsgVO , Model model) throws Exception {
		snsService.AddSNS_Msg(MsgVO);
		return "TestChat";
	}
	
	@RequestMapping(value = "/ShowMSG.do" , method = RequestMethod.POST)
	public String ShowMSG(MsgVO msgVO, Model model) throws Exception {
		ArrayList<MsgVO> arr = snsService.getAllMSG(msgVO);
		model.addAttribute("alist", arr);
		return "MsgBox";
	}
	
	@RequestMapping(value = "/msg_pop.do")
	public String msg_pop(MsgVO msgVO, Model model) throws Exception {
		snsService.upSns_Msg(msgVO);
		MsgVO imsi = snsService.getMSG(msgVO);
		model.addAttribute("msgpop", imsi);
		return "Msgpop";
	}
	
	@RequestMapping(value = "/friendTab.do")
	public String friendAll(Model model) throws Exception {
		return "FriendTab";
	}

	@RequestMapping(value = "/sessOK.do")
	public String mypage(MemVO memVO, Model model) throws Exception {
		arr = snsService.getFriend(memVO);// 1. 전역에 두고
		return "redirect:/mainpage.do";// mainpage.do로 이동
		// 메인페이지에 표기주소가 sessOK.do로 나와서 표기주소 바꾸기 위해 이렇게 했습니다.
	}

	@RequestMapping(value = "/userUpdate.do")
	public String mypageUpdate(Model model) throws Exception {
		return "MypageUpdate";
	}

	@RequestMapping(value = "/mypage.do")
	public String mypage(Model model) throws Exception {
		return "Mypage";
	}

	@RequestMapping(value = "/mainpage.do") // sessOK.do에서 넘어옴
	public String mainPage(Model model, HttpServletRequest request) throws Exception {
		MemVO memVO = (MemVO)request.getSession().getAttribute("loginVO");
		friendsFeed = snsService.friendsFeed(memVO);
		model.addAttribute("alist", arr);// 전역에 등록된걸 여기서 모델에 등록
		model.addAttribute("friendsFeed", friendsFeed);
		return "mainpage";// 이러면 주소노출이 mainpage.do
	}

	@RequestMapping(value = "/loginpage.do")
	public String loginpage(Model model) throws Exception {
		return "login";
	}

	@RequestMapping(value = "/updatepage.do")
	public String updatepage(Model model) throws Exception {
		return "update";
	}
	
	@RequestMapping(value = "/logout.do")
	public String logout(@ModelAttribute("memVO") MemVO memVO, Model model) throws Exception {
		snsService.logoutmem(memVO);
		return "logout";
	}

	@RequestMapping(value = "/signup.do")
	public String signup(Model model) throws Exception {
		return "signup";
	}

	@RequestMapping(value = "/memInsert.do") // 회원가입
	public String memInsert(@ModelAttribute("memVO") MemVO memVO, Model model) {
		try {
			snsService.insertMem(memVO);
			MemVO loginVO = snsService.login(memVO);
			model.addAttribute("loginVO", loginVO);
			model.addAttribute("success", true);
		} catch (Exception e) {
			model.addAttribute("success", false);
			e.printStackTrace();
		}
		return "JoinSuccess";
	}

	@RequestMapping(value = "/login.do")
	public String login(@ModelAttribute("memVO") MemVO memVO, Model model) {
		MemVO loginVO = snsService.login(memVO);
		if (loginVO != null) {
			model.addAttribute("loginVO", loginVO);
			snsService.loginmem(memVO);
		}
		return "setSession";
	}

	@ResponseBody
	@RequestMapping(value = "/idCheck.do", method = RequestMethod.POST)
	public String idCheck(@ModelAttribute("memVO") MemVO memVO, Model model) {
		return (snsService.getOne(memVO)!=null) ? "fail" : "success";
	}

	@ResponseBody
	@RequestMapping(value = "/FrReload.do", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String FrReload(MemVO memVO, Model model) throws Exception {
		arr = snsService.getFriend(memVO);
		model.addAttribute("alist", arr);
		String imsi = "";
		if (arr != null) {
			for (FriendVO vo : arr) {
				imsi += vo.getFrid() + "///" + vo.getFrname() + "///" + vo.getLoginstate() + "||";
			}
		}
		return imsi;
	}
	
	
	@ResponseBody
	@RequestMapping("/addFriend.do")
	public String addFriend(@RequestParam Map<String, Object> param, Model model) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", param.get("id"));
		params.put("fid", param.get("fid"));
		snsService.addFriend(params);

		params.put("id", param.get("fid"));
		params.put("fid", param.get("id"));
		snsService.addFriend(params);
		
		snsService.upSns_frd(param);
		
		return "TestChat";
	}
	
	@ResponseBody
	@RequestMapping("/NoFriend.do")
	public String NoFriend(@RequestParam Map<String, Object> param, Model model) throws Exception {		
		snsService.upSns_frd(param);
		
		return "TestChat";
	}
	
	@ResponseBody
	@RequestMapping(value="/auth.do")
	public String mailSending(HttpServletRequest request, Model model) throws Exception {

		StringBuffer temp = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < 6; i++) {
			temp.append(rnd.nextInt(10));
		} 
		final String subject = "본인인증 메일입니다.";
		final String from = "kgemailtest4@gmail.com";
		final String to = request.getParameter("e_mail");

		final String content = System.getProperty("line.separator") + "인증번호를 회원가입란에 입력해주세요."
				+ System.getProperty("line.separator") + System.getProperty("line.separator") + "인증 번호는 " + temp
				+ " 입니다." + System.getProperty("line.separator") + System.getProperty("line.separator");

		try {
			MimeMessage mail = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mail, true, "UTF-8");
			messageHelper.setSubject(subject);
			messageHelper.setTo(to);
			messageHelper.setFrom(from);
			messageHelper.setText(content);
			mailSender.send(mail);
		} catch (Exception e) {

		}
		return temp.toString();
	}
	
	@RequestMapping(value = "/addGroup.do")
	public void addGroup(GroupVO groupVO, Model model) {
		snsService.addGroup(groupVO);
	}
	
	@ResponseBody
	@RequestMapping(value = "/srcajax.do", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String srcajax(MemVO memVO,Model model) throws Exception {
		SearchajaxVo count1 = snsService.searchajax(memVO);
		return count1.getMmcnt() + "///" + count1.getTxcnt() + "///" + count1.getGrcnt();
	}
	
	@RequestMapping(value = "/writeFeed.do")
	public String writeFeed(TextVO textVO, @RequestParam("txtFile2")MultipartFile[] files, HttpServletRequest request) throws Exception {
		String imgname = "";
		if(!files[0].isEmpty()) {
			String root_path = request.getSession().getServletContext().getRealPath("/");
			String attach_path = "/resources/img/feed/";
			for(int i=0 ; i<files.length ; i++) {
				String extension = FilenameUtils.getExtension(files[i].getOriginalFilename());
				String imsi = snsService.getFeedCount() + "_" + i + "." + extension;
				File file = new File(root_path + attach_path + imsi);
				files[i].transferTo(file);
				imgname += imsi + "/";
			}
			imgname = imgname.substring(0, imgname.length()-1);
			textVO.setTXT_FILE(imgname);
			snsService.insertImg(textVO);
	               
		}
		else {
			snsService.insertText(textVO);
		}      
		return "redirect:/mainpage.do";
	}
	
	@RequestMapping(value = "/SendMSG.do" , method = RequestMethod.POST)
	   public String SendMSG(MsgVO msgVO, Model model, HttpServletRequest request) throws Exception {
	      model.addAttribute("read_id", request.getParameter("read_id"));
	      return "SendMessage";
	   }
	
	@RequestMapping(value = "/SendMSG2.do")
	public String SendMSG2(MsgVO msgVO, Model model) throws Exception {
		snsService.AddSNS_Msg2(msgVO);
		return "SendMessage";
	}
	
	@RequestMapping(value = "/getFeed.do")
	public String getFeed(GoodBadVO goodBadVO, CommentVO commentVO, TextVO textVO, MemVO memVO, Model model, HttpServletRequest request) {
		String id = request.getParameter("id");
		String TXT_NUM = request.getParameter("TXT_NUM");
		//댓글
		ArrayList<CommentVO> commentList = snsService.getComments(commentVO);
		//피드
		textVO = snsService.getFeed(TXT_NUM);
		//피드 게시자
		memVO.setId(id);
		MemVO feedOwner = snsService.getOne(memVO);
		
		//좋아요 싫어요 수
		ArrayList<GoodBadVO> result = snsService.getGoodBad(goodBadVO);
		int like=0;
		int hate=0;
		for(GoodBadVO vo : result) {
			if(vo.getGOODBAD().equals("O")) {
				like++;
			} else if(vo.getGOODBAD().equals("X")) {
				hate++;
			}
		}
		goodBadVO.setLike(like);
		goodBadVO.setHate(hate);
		
		
		model.addAttribute("commentList", commentList);
		model.addAttribute("textVO", textVO);
		model.addAttribute("feedOwner", feedOwner);
		model.addAttribute("goodBadVO", goodBadVO);
		model.addAttribute("result", result);
		
		return "news";
	}
	
	@ResponseBody
	@RequestMapping(value = "/goodButtonPlus.do")
	public String goodButtonPlus(GoodBadVO goodBadVO, HttpServletRequest request) {
		snsService.goodInsertPlus(goodBadVO);
		return Integer.toString(goodBadVO.getLike());
	}
	
	@ResponseBody
	@RequestMapping(value = "/badButtonPlus.do")
	public String badButtonPlus(GoodBadVO goodBadVO, HttpServletRequest request) {
		
		snsService.badInsertPlus(goodBadVO);
		return Integer.toString(goodBadVO.getHate());
	}

	@ResponseBody
	@RequestMapping(value = "/deleteGoodBad.do")
	public String deleteGoodBad(GoodBadVO goodBadVO, HttpServletRequest request) {
		snsService.deleteGoodBad(goodBadVO);
		if(request.getParameter("data").equals("good")) {
			return Integer.toString(snsService.getLikeCount(goodBadVO));
		} else {
			return Integer.toString(snsService.getHateCount(goodBadVO));
		}
	}

	@ResponseBody
	@RequestMapping(value = "/updateGoodBad.do")
	public Map<String, Object> updateGoodBad(GoodBadVO goodBadVO, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(request.getParameter("data").equals("good")) {
			goodBadVO.setGOODBAD("O");
		} else {
			goodBadVO.setGOODBAD("X");
		}
		snsService.updateGoodBad(goodBadVO);
		map.put("like",snsService.getLikeCount(goodBadVO));
		map.put("hate",snsService.getHateCount(goodBadVO));
		
		return map;
	}
	
	
	@RequestMapping(value = "addComment.do")
	public String addComment(CommentVO commentVO, Model model) {
		snsService.addComment(commentVO);
		return "redirect:/mainpage.do";
	}

	@RequestMapping(value = "/getRecentFeed.do")
	public String getRecentFeed(GoodBadVO goodBadVO, CommentVO commentVO, TextVO textVO, MemVO memVO, Model model, HttpServletRequest request) {
		String TXT_NUM = request.getParameter("TXT_NUM");
		//댓글
		ArrayList<CommentVO> commentList = snsService.getComments(commentVO);
		//피드
		textVO = snsService.getFeed(TXT_NUM);
		//피드 게시자
		memVO.setId(textVO.getId());
		MemVO feedOwner = snsService.getOne(memVO);
		
		//좋아요 싫어요 수
		ArrayList<GoodBadVO> result = snsService.getGoodBad(goodBadVO);
		int like=0;
		int hate=0;
		for(GoodBadVO vo : result) {
			if(vo.getGOODBAD().equals("O")) {
				like++;
			} else if(vo.getGOODBAD().equals("X")) {
				hate++;
			}
		}
		goodBadVO.setLike(like);
		goodBadVO.setHate(hate);
		
		
		model.addAttribute("textVO", textVO);
		model.addAttribute("commentList", commentList);
		model.addAttribute("feedOwner", feedOwner);
		model.addAttribute("goodBadVO", goodBadVO);
		model.addAttribute("result", result);
		
		return "news";
	}

	@RequestMapping(value = "/friendpage.do")
	public String friendpage(Model model) throws Exception {
		return "Friendpage";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getsearchFeed.do")
	public ArrayList<Integer> getsearchFeed(Model model, MemVO memVO, CommentVO commentVO, GoodBadVO goodBadVO, HttpServletRequest request) {
		String txt = request.getParameter("txt");
		ArrayList<Integer> myFeedTXT_NUM = snsService.getTXT(txt);
		return myFeedTXT_NUM;
	}
	@ResponseBody
	@RequestMapping(value = "/getMyFeed.do")
	public ArrayList<Integer> getMyFeeds(Model model, MemVO memVO, CommentVO commentVO, GoodBadVO goodBadVO, HttpServletRequest request) {
		String id = request.getParameter("id");
		ArrayList<Integer> myFeedTXT_NUM = snsService.getTXT_NUM(id);
		return myFeedTXT_NUM;
	}
	@ResponseBody
	@RequestMapping(value = "/getscFeed.do")
	public String getscFeed(Model model, MemVO memVO, CommentVO commentVO, GoodBadVO goodBadVO, HttpServletRequest request) throws Exception {
		String searchTxt2 = request.getParameter("txt");
		ArrayList<TextVO> myFeedTXT_sc = snsService.searchtxt(searchTxt2);
		String imsiarr = "";
		for(TextVO temp : myFeedTXT_sc) {
			imsiarr += temp.getId() + "///";
			imsiarr += temp.getTXT_NUM() + "||";

		}
		return imsiarr;
	}
	@RequestMapping(value = "/getMyFeed2.do")
	public String getMyFeed2(Model model, MemVO memVO, CommentVO commentVO, GoodBadVO goodBadVO, HttpServletRequest request) {

		return "center2";
	}
}
