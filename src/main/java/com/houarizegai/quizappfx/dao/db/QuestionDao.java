
package com.houarizegai.quizappfx.dao.db;

import com.houarizegai.quizappfx.dao.vo.QuestionVo;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDao extends MysqlConnection {
    
    public List<QuestionVo> getQuestion(int type) {
        Connection con = getConnection();
        if(con == null)
            return null;
        List<QuestionVo> questions = new ArrayList<>();
        String sql = "SELECT question, response1, response2, response3, response4 FROM questions WHERE type=?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, type);
            ResultSet rs = ps.executeQuery();
            QuestionVo question;
            while(rs.next()) {
                question = new QuestionVo();
                question.setQuestion(rs.getString("question"));
                question.setResponse(getRandom(new String[]{rs.getString("response1"), rs.getString("response2"), 
                    rs.getString("response3"), rs.getString("response4")}));
                question.setSolution(rs.getString("response1"));
                questions.add(question);
            }
            
        } catch(SQLException se) {
            System.out.println("Error msg: " + se.getMessage());
        }
        return questions;
        
    }
        
    public String[] getRandom(String[] input) {
        int[] index = getRandom();
        String[] result = new String[4];
        
        for(int i = 0; i < 4; i++) {
            result[i] = input[index[i]];
        }
        
        return result;
    }
    
    public int[] getRandom() {
        SecureRandom r = new SecureRandom();
        int[] v = new int[4];
        int n;
        int i = 1;
        boolean redendance;
        v[0] = r.nextInt(4);
        while(i < 4) {
            redendance = false;
            
            n = r.nextInt(4);
            for(int j = 0; j < i; j++) {
                if(v[j] == n) {
                    redendance = true;
                    break;
                }
            }
            
            if(!redendance) {
                v[i] = n;
                i++;
            }
        }
        
        return v;
    }

}
