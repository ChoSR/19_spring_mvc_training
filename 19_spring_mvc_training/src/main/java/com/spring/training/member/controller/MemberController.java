package com.spring.training.member.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.training.member.dto.MemberDto;
import com.spring.training.member.service.MemberService;


@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value="/main" , method=RequestMethod.GET)
	public ModelAndView main() {
		return new ModelAndView("member/main");
	}
	
	@RequestMapping(value="/register" , method=RequestMethod.GET)
	public ModelAndView register() {
		return new ModelAndView("member/register");
	}
	
	@RequestMapping(value="/register" , method=RequestMethod.POST)
	public @ResponseBody String register(MemberDto memberDto , HttpServletRequest request) throws Exception {
		
		memberService.addMember(memberDto);
		
		String jsScript = "<script>";
			   jsScript += "alert('You are now a member.');";
			   jsScript += "location.href='"+ request.getContextPath()+"/member/main'";
			   jsScript += "</script>";
			   
		return jsScript;
		
	}
	
	
	@RequestMapping(value="/overlappedId" , method=RequestMethod.POST)
	public ResponseEntity<Object> overlapped(@RequestParam("memberId") String memberId) throws Exception{
		return new ResponseEntity<Object>(memberService.checkOverlappedId(memberId), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/login" , method=RequestMethod.GET)
	public ModelAndView login() throws Exception {
		return new ModelAndView("member/login");
	}
	
	@RequestMapping(value="/login" , method=RequestMethod.POST)
	public @ResponseBody String login(MemberDto memberDto , HttpServletRequest request) throws Exception {
		
		String jsScript = "";
		if (memberService.loginMember(memberDto) != null) {
			
			HttpSession session = request.getSession();
			session.setAttribute("memberId", memberDto.getMemberId());
			
			jsScript += "<script>";
			jsScript += "alert('success login!');";
			jsScript += "location.href='"+ request.getContextPath()+"/member/main'";
			jsScript += "</script>";
		} 
		else {
			jsScript += "<script>";
			jsScript += "alert('check your Id or Password!');";
			jsScript += "history.back(-1);";
			jsScript += "</script>";
		}
		
		return jsScript;
		
	}
	
	
	@RequestMapping(value="/memberList" , method=RequestMethod.GET)
	public ModelAndView memberList() throws Exception {
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("member/memberList");
		mv.addObject("memberList" , memberService.getMemberList());
		
		return mv;
	
	}	
	
	@RequestMapping(value="/myPage" , method=RequestMethod.GET)
	public ModelAndView myPage(HttpServletRequest request , Model model) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("member/myPage");
		
		HttpSession session = request.getSession();
		mv.addObject("memberDto" , memberService.getMemberDetail((String)session.getAttribute("memberId")));
		
		return mv;
		
	}	
	
	@RequestMapping(value="/logout" , method=RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return new ModelAndView("redirect:/member/main");
	}
	
	@RequestMapping(value="/modifyMember" , method=RequestMethod.POST)
	public @ResponseBody String modifyMember(MemberDto memberDto , HttpServletRequest request) throws Exception {
		
		String jsScript = "";
		
		if (memberService.modifyMember(memberDto)) {
			jsScript = "<script>";
			jsScript += "alert('success update!');";
			jsScript += "location.href='"+ request.getContextPath()+"/member/main'";
			jsScript += "</script>";
		} 
		else {
			jsScript = "<script>";
			jsScript += "alert('check your password!');";
			jsScript += "history.back(-1);";
			jsScript += "</script>";
		}
		
		return  jsScript;
		
	}
	
	
	@RequestMapping(value="/removeMember" , method=RequestMethod.GET)
	public ModelAndView removeMember() {
		return new ModelAndView("member/delete");
	}
	
	
	@RequestMapping(value="/removeMember" , method=RequestMethod.POST)
	public @ResponseBody String removeMember(MemberDto memberDto , HttpServletRequest request) throws Exception {
		
		String jsScript = "";
		
		if (memberService.removeMember(memberDto)) {
			HttpSession session = request.getSession();
			session.invalidate();
			jsScript += "<script>";
			jsScript += "alert('Your account has been deleted successfully!');";
			jsScript += "location.href='" + request.getContextPath() + "/member/main'";
			jsScript += "</script>";
		} 
		else {
			jsScript += "<script>";
			jsScript += "alert('Check your Id or Password');";
			jsScript += "history.back(-1);";
			jsScript += "</script>";
		}
		
		return jsScript;
		
	}
	
	
	@RequestMapping(value="/memberExcelExport" , method=RequestMethod.GET)
	public void memberExcelExport(HttpServletResponse response , @RequestParam Map<String, String> dateMap) throws Exception {
		  
		SimpleDateFormat fileSdf = new SimpleDateFormat("yyyy_MM_dd_hh_mm");
		String makeFileTime = fileSdf.format(new Date());
		String makeFileName = makeFileTime + "_memberList.xls";
		
	    // 워크북 생성
	    Workbook wb = new HSSFWorkbook();
	    Sheet sheet = wb.createSheet("회원리스트");
	    Row row = null;
	    Cell cell = null;

	    int rowNo = 0;


	    // 테이블 헤더용 스타일
	    CellStyle headStyle = wb.createCellStyle();
	    // 가는 경계선
	    headStyle.setBorderTop(BorderStyle.THIN);
	    headStyle.setBorderBottom(BorderStyle.THIN);
	    headStyle.setBorderLeft(BorderStyle.THIN);
	    headStyle.setBorderRight(BorderStyle.THIN);


	    // 노란색 배경
	    headStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
	    headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	    // 가운데 정렬
	    headStyle.setAlignment(HorizontalAlignment.CENTER);


	    // 데이터용 경계 스타일 테두리만 지정
	    CellStyle bodyStyle = wb.createCellStyle();
	    bodyStyle.setBorderTop(BorderStyle.THIN);
	    bodyStyle.setBorderBottom(BorderStyle.THIN);
	    bodyStyle.setBorderLeft(BorderStyle.THIN);
	    bodyStyle.setBorderRight(BorderStyle.THIN);

	    // 헤더 생성
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("회원아이디");
	    cell = row.createCell(1);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("회원이름");
	    cell = row.createCell(2);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("성별");
	    cell = row.createCell(3);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("휴대폰번호");
	    cell = row.createCell(4);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("문자수신여부");
	    cell = row.createCell(5);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("주소");
	    cell = row.createCell(6);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("이메일");
	    cell = row.createCell(7);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("이메일수신여부");
	    
		for (MemberDto memberDto :  memberService.getMemberList()) {
			row = sheet.createRow(rowNo++);
	        cell = row.createCell(0);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(memberDto.getMemberId());
	        cell = row.createCell(1);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(memberDto.getMemberNm());
	        cell = row.createCell(2);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(memberDto.getSex());
	        cell = row.createCell(3);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(memberDto.getHp());
	        cell = row.createCell(4);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(memberDto.getSmsstsYn());
	        cell = row.createCell(5);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(memberDto.getRoadAddress() + " " + memberDto.getJibunAddress() + " " + memberDto.getNamujiAddress());
	        cell = row.createCell(6);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(memberDto.getEmail());
	        cell = row.createCell(7);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(memberDto.getEmailstsYn());

		} 

	    response.setContentType("ms-vnd/excel");
	    response.setHeader("Content-Disposition", "attachment;filename="+makeFileName);

	    // 엑셀 출력
	    wb.write(response.getOutputStream());
	    wb.close();

		
	}
	
}
