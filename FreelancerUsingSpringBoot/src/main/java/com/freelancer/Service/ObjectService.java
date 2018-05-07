package com.freelancer.Service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.freelancer.Entity.Project;
import com.freelancer.POJO.ProjectDetail;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class ObjectService{

    @PersistenceContext
    private EntityManager em;

    public List getAverageBid(){
        List<Object> results = em.createNativeQuery("SELECT * FROM user").getResultList();
        return results;
    }


    public List<ProjectDetail> getOpenProjects(long userId){
        String query="select c.name,sub1.* from (select a.id,a.freelancer_id,a.employer_id,a.title,a.main_skill_id,a.budget_range," +
                "a.budget_period,COALESCE(avg(b.bid_amount),0) as average ,count(b.project_id) as count from project a left outer " +
                "join bid b on a.id=b.project_id group by a.id,a.title) as sub1,user c where c.id=sub1.employer_id and" +
                " employer_id!=" + userId + " and sub1.freelancer_id IS NULL";
        List<Object[]> results = em.createNativeQuery(query).getResultList();
        List<ProjectDetail> processedResults = new ArrayList<>();
        for (Object obj[]:results) {
            System.out.println(obj);
            ProjectDetail projectDetail = new ProjectDetail();
            projectDetail.setEmployerName(obj[0].toString());
            projectDetail.setProjectId(Long.parseLong(obj[1]+""));
            projectDetail.setEmployerId(Long.parseLong(obj[3]+""));
            projectDetail.setProjectTitle(obj[4].toString());
            projectDetail.setProjectSkill(obj[5].toString());
            projectDetail.setProjectBudget(obj[6].toString());
            projectDetail.setProjectPeriod(obj[7].toString());
            projectDetail.setAverageBid(Double.parseDouble(obj[8]+""));
            projectDetail.setBidCount(Long.parseLong(obj[9]+""));
            processedResults.add(projectDetail);
        }
        return processedResults;
    }

    public List<ProjectDetail> getProjectDetail(long projectId){
        String query="select sub1.*,b.link from (select a.id,a.freelancer_id,a.document_id,a.description,a.employer_id,a.title" +
                ",a.main_skill_id,a.budget_range,a.budget_period,COALESCE(avg(b.bid_amount),0) as average,count(b.project_id) " +
                "as count from project a left outer join bid b on a.id=b.project_id group by a.id,a.title) as sub1,attachments " +
                "b where b.id=sub1.document_id  and sub1.id=" + projectId;
        List<Object[]> results = em.createNativeQuery(query).getResultList();
        List<ProjectDetail> processedResults = new ArrayList<>();
        for (Object obj[]:results) {
            System.out.println(obj.toString());
            ProjectDetail projectDetail = new ProjectDetail();
            projectDetail.setProjectId(Long.parseLong(obj[0]+""));
            projectDetail.setFreelancerId(Long.parseLong(obj[1]+""));
            projectDetail.setDocumentId(Long.parseLong(obj[2]+""));
            projectDetail.setProjectDescription(obj[3].toString());
            projectDetail.setEmployerId(Long.parseLong(obj[4]+""));
            projectDetail.setProjectTitle(obj[5].toString());
            projectDetail.setProjectSkill(obj[6].toString());
            projectDetail.setProjectBudget(obj[7].toString());
            projectDetail.setProjectPeriod(obj[8].toString());
            projectDetail.setAverageBid(Double.parseDouble(obj[9]+""));
            projectDetail.setBidCount(Long.parseLong(obj[10]+""));
            projectDetail.setDocumentPath(obj[11].toString());
            processedResults.add(projectDetail);
        }
        return processedResults;
    }


    public List<ProjectDetail> getUserOpenProjects(long userId){
        String query="select a.id,a.employer_id,a.title,a.budget_range,a.budget_period,COALESCE(avg(b.bid_amount),0) as average ," +
                "count(b.project_id) as count from project a left outer join bid b on a.id=b.project_id and b.bid_status='BID_SENT' " +
                "where a.employer_id=" + userId +  " and a.freelancer_id is NULL group by a.id,a.title";
        List<Object[]> results = em.createNativeQuery(query).getResultList();
        List<ProjectDetail> processedResults = new ArrayList<>();
        for (Object obj[]:results) {
            System.out.println(obj.toString());
            ProjectDetail projectDetail = new ProjectDetail();
            projectDetail.setProjectId(Long.parseLong(obj[0]+""));
            projectDetail.setEmployerId(Long.parseLong(obj[1]+""));
            projectDetail.setProjectTitle(obj[2].toString());
            projectDetail.setProjectBudget(obj[3].toString());
            projectDetail.setProjectPeriod(obj[4].toString());
            projectDetail.setAverageBid(Double.parseDouble(obj[5]+""));
            projectDetail.setBidCount(Long.parseLong(obj[6]+""));
            processedResults.add(projectDetail);
        }
        return processedResults;
    }

    public List<ProjectDetail> getUserProgressProjects(long userId){
        String query="select c.name,sub1.* from (select a.id as project_id,a.employer_id,a.freelancer_id,a.title,a.end_date,b.bid_amount," +
                "COALESCE(avg(b.bid_amount),0) as average ,count(b.project_id) as count from project a left outer join bid b " +
                "on a.id=b.project_id where b.bid_status!='BID_SENT' and a.employer_id=" + userId + " group by a.id,a.title)" +
                " as sub1,user c where c.id=sub1.freelancer_id";
        List<Object[]> results = em.createNativeQuery(query).getResultList();
        List<ProjectDetail> processedResults = new ArrayList<>();
        for (Object obj[]:results) {
            System.out.println(obj.toString());
            ProjectDetail projectDetail = new ProjectDetail();
            projectDetail.setFreelancerName(obj[0].toString());
            projectDetail.setProjectId(Long.parseLong(obj[1]+""));
            projectDetail.setEmployerId(Long.parseLong(obj[2]+""));
            projectDetail.setFreelancerId(Long.parseLong(obj[3]+""));
            projectDetail.setProjectTitle(obj[4].toString());
            projectDetail.setEndDate(obj[5].toString());
            projectDetail.setBidAmount(obj[6].toString());
            projectDetail.setAverageBid(Double.parseDouble(obj[7]+""));
            projectDetail.setBidCount(Long.parseLong(obj[8]+""));

            processedResults.add(projectDetail);
        }
        return processedResults;
    }


    public List<ProjectDetail> getUserBidProjects(long userId){
        String query="select c.name,sub1.* from (select a.id,a.employer_id,a.title,a.main_skill_id,a.budget_range,a.budget_period," +
                "b.bid_amount,b.bid_status,b.user_id,COALESCE(avg(b.bid_amount),0) as average ,count(b.project_id) as " +
                "count from project a left outer join bid b on a.id=b.project_id group by a.id,b.user_id) as " +
                "sub1,user c where c.id=sub1.employer_id and sub1.employer_id!="+ userId+ " and sub1.user_id=" + userId;
        List<Object[]> results = em.createNativeQuery(query).getResultList();
        List<ProjectDetail> processedResults = new ArrayList<>();
        for (Object obj[]:results) {
            System.out.println(obj.toString());
            ProjectDetail projectDetail = new ProjectDetail();
            projectDetail.setEmployerName(obj[0].toString());
            projectDetail.setProjectId(Long.parseLong(obj[1]+""));
            projectDetail.setEmployerId(Long.parseLong(obj[2]+""));
            projectDetail.setProjectTitle(obj[3].toString());
            projectDetail.setProjectSkill(obj[4].toString());
            projectDetail.setProjectBudget(obj[5].toString());
            projectDetail.setProjectPeriod(obj[6].toString());
            projectDetail.setBidAmount(obj[7].toString());
            projectDetail.setBidStatus(obj[8].toString());
            projectDetail.setAverageBid(Double.parseDouble(obj[10]+""));
            projectDetail.setBidCount(Long.parseLong(obj[11]+""));

            processedResults.add(projectDetail);
        }
        return processedResults;
    }

}
