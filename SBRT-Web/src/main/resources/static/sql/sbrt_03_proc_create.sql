
DELIMITER $$
CREATE OR REPLACE PROCEDURE `sbrt`.`PROC_MAKE_CUR_ALLOC_PL_INFO`()
BEGIN
	DECLARE var_day_div VARCHAR(5);
	
	SELECT FN_GET_DAY_DIV(CURDATE()) INTO var_day_div;
	

	-- 기존 데이터 삭제
	DELETE FROM BRT_DAY_ALLOC_PL_INFO;	
	
	INSERT INTO BRT_DAY_ALLOC_PL_INFO
	SELECT
		CURDATE() AS OPER_DT
		,REP_ROUT_ID
		,WAY_DIV
		,ALLOC_NO
		,VHC_ID
		,NOW() AS UPD_DTM
	FROM BRT_ALLOC_PL_MST
	WHERE DAY_DIV = var_day_div
	ORDER BY REP_ROUT_ID ASC;
	

END $$
DELIMITER ;;

DELIMITER $$
CREATE OR REPLACE PROCEDURE `sbrt`.`PROC_MAKE_CUR_OPER_ALLOC_PL_NODE_INFO`()
BEGIN

	-- BRT_OPER_ALLOC_PL_NODE_INFO 에서 현재(오늘) 운행할 데이터를 생성			
	
	DECLARE var_today_dt DATE;
	DECLARE var_week_day_no INTEGER;
	DECLARE var_is_holiday INTEGER;
	DECLARE var_day_div VARCHAR(5);
	
	
	
	SELECT CURDATE() INTO var_today_dt;	
	SELECT DAYOFWEEK(var_today_dt) INTO var_week_day_no;
		
	SELECT COUNT(HOLI_DT)
		INTO var_is_holiday
	FROM BRT_HOLI_MST
	WHERE HOLI_DT = var_today_dt;
	
	IF(var_is_holiday > 0 OR var_week_day_no = 1 OR var_week_day_no = 7) THEN -- 휴일여부 확인 (1:일요일, 7:토요일)
		SET var_day_div = 'DY002';
	ELSE
		SET var_day_div = 'DY001';
	END IF;	
	
	
	
	-- 기존 데이터 삭제
	DELETE FROM BRT_CUR_OPER_ALLOC_PL_NODE_INFO;
		
	
	INSERT INTO BRT_CUR_OPER_ALLOC_PL_NODE_INFO
	SELECT
		CURDATE() AS OPER_DT
		,REP_ROUT_ID
		,ROUT_ID
		,COR_ID
		,OPER_SN
		,NODE_ID
		,NODE_SN
		,ALLOC_NO
		,NODE_TYPE
		,DPRT_TM
		,ARRV_TM			
		,NOW() AS UPD_DTM
	FROM BRT_OPER_ALLOC_PL_NODE_INFO
	WHERE DAY_DIV = var_day_div
		-- AND REP_ROUT_ID = 'RR00000002' -- test
	ORDER BY REP_ROUT_ID, OPER_SN ASC;



END $$
DELIMITER ;;

DELIMITER $$
CREATE OR REPLACE PROCEDURE `sbrt`.`PROC_MAKE_CUR_OPER_ALLOC_PL_ROUT_INFO`()
BEGIN

	-- BRT_OPER_ALLOC_PL_ROUT_INFO 에서 현재(오늘) 운행할 데이터를 생성	

	
	DECLARE var_today_dt DATE;
	DECLARE var_week_day_no INTEGER;
	DECLARE var_is_holiday INTEGER;
	DECLARE var_day_div VARCHAR(5);
	
		
	SELECT CURDATE() INTO var_today_dt;	
	SELECT DAYOFWEEK(var_today_dt) INTO var_week_day_no;
	
	SELECT COUNT(HOLI_DT)
		INTO var_is_holiday
	FROM BRT_HOLI_MST
	WHERE HOLI_DT = var_today_dt;
	
	IF(var_is_holiday > 0 OR var_week_day_no = 1 OR var_week_day_no = 7) THEN -- 휴일여부 확인 (1:일요일, 7:토요일)
		SET var_day_div = 'DY002';
	ELSE
		SET var_day_div = 'DY001';
	END IF;	

	
	-- 기존 데이터 삭제
	DELETE FROM BRT_CUR_OPER_ALLOC_PL_ROUT_INFO;
	
	
	INSERT INTO BRT_CUR_OPER_ALLOC_PL_ROUT_INFO
	SELECT
		CURDATE() AS OPER_DT
		,REP_ROUT_ID
		,ROUT_ID
		,OPER_SN
		,ALLOC_NO
		,COR_ID		
		,ROUT_ST_TM
		,ROUT_ED_TM			
		,NOW() AS UPD_DTM
	FROM BRT_OPER_ALLOC_PL_ROUT_INFO
	WHERE DAY_DIV = var_day_div
		AND REP_ROUT_ID = 'RR00000002' -- test
	ORDER BY REP_ROUT_ID, OPER_SN ASC;


END $$
DELIMITER ;;

DELIMITER $$
CREATE OR REPLACE PROCEDURE `sbrt`.`PROC_MAKE_LINK_AVG_SPD`()
    COMMENT 'SBRT 링크 평균속도 생성(최근5분)'
BEGIN

	-- 이전데이터 clear
	UPDATE BMS_LINK_MST
	SET AVRG_SPD = NULL
		,UPD_DTM = NOW()
	WHERE 
		USE_YN = 'Y' AND SBRT_YN = 'Y';


	-- 데이터 갱신
	UPDATE BMS_LINK_MST A
	INNER JOIN (
		SELECT
			B.OPER_DT AS OPER_DT
			,B.REP_ROUT_ID AS REP_ROUT_ID
			,B.VHC_ID AS VHC_ID
			,B.UPD_DTM AS UPD_DTM
			,B.VHC_NO AS VHC_NO
			,B.COR_ID AS COR_ID
			,B.ROUT_ID AS ROUT_ID
			,B.ALLOC_NO AS ALLOC_NO
			,B.OPER_SN AS OPER_SN
			,B.LINK_ID AS LINK_ID
			,AVG(B.EVT_DATA) AS AVG_SPD
		FROM BRT_OPER_EVENT_HIS B	
		WHERE B.LINK_ID IS NOT NULL
			AND B.OCR_DTM > DATE_ADD(NOW(), INTERVAL -5 MINUTE)
			AND B.EVT_TYPE = 'ET007'
			AND B.EVT_DATA IS NOT NULL
		GROUP BY B.LINK_ID
	) C ON A.LINK_ID = C.LINK_ID
	SET A.AVRG_SPD = C.AVG_SPD
		,A.UPD_DTM = NOW();
END $$
DELIMITER ;;

DELIMITER $$
CREATE OR REPLACE PROCEDURE `sbrt`.`PROC_MAKE_OPER_ALLOC_PL_NODE_INFO`(
	IN `param_course_id` VARCHAR(10),
	IN `param_route_id` VARCHAR(10),
	IN `param_day_div` VARCHAR(5),
	IN `param_oper_sn` INT,
	IN `param_alloc_cnt` INT
)
    COMMENT '노드별 운행배차계획 정보 생성'
BEGIN
	
	-- insert용
	DECLARE var_rep_route_id VARCHAR(10); -- 대표노선아이디
	DECLARE var_route_id VARCHAR(10);
	DECLARE var_day_div VARCHAR(5);
    DECLARE var_way_div VARCHAR(5);
	DECLARE var_alloc_no INT DEFAULT(0);
	DECLARE var_oper_sn INT;
	DECLARE var_node_id VARCHAR(10);
	DECLARE var_course_id VARCHAR(10);
	DECLARE var_node_sn INT;
	DECLARE var_node_type VARCHAR(5);	
	DECLARE var_arrv_tm TIME;	
	DECLARE var_dprt_tm TIME;
    DECLARE var_prev_dprt_tm TIME; -- 이전 노드 출발 시각
	
	DECLARE var_link_id VARCHAR(10); 
    DECLARE var_link_len INT; 
	DECLARE var_prev_diff_sec INT DEFAULT(0); -- 이전 노드에서부터 걸리는 기간(초)
	DECLARE var_next_diff_sec INT DEFAULT(0); -- 다음 노드까지 걸리는 시간(초)
	DECLARE var_add_arrv_sec INT DEFAULT(0); -- 다음 노드의 도착시간 연장 시간(초)
	
	DECLARE var_next_node_id VARCHAR(10);
    DECLARE var_next_node_sn INT;
	DECLARE var_next_node_type VARCHAR(5);
	DECLARE var_next_cross_id VARCHAR(10);
	DECLARE var_phase_remain_sec INT DEFAULT(0);
    DECLARE var_phase_remain_sec_tmp INT DEFAULT(0);
	DECLARE var_enter_phase_no1 INT DEFAULT(0);	
	DECLARE var_enter_phase_no2 INT DEFAULT(0);	
	DECLARE var_enter_phase_no3 INT DEFAULT(0);	
    DECLARE var_sig_ctr_type VARCHAR(5);
    
	
	DECLARE var_first_node_sn INT;
	DECLARE var_last_node_sn INT;	
	DECLARE var_route_st_tm VARCHAR(8);	
	DECLARE var_route_ed_tm VARCHAR(8);
	
	DECLARE var_min_stop_sec INT DEFAULT(0);	 -- 최소 정차시간(sec)
	DECLARE var_max_stop_sec INT DEFAULT(0);	 -- 최대 정차시간(sec)
    DECLARE var_stop_sec_peak INT DEFAULT(0);	 -- 정류장 필요 정차시간(첨두시)	
    DECLARE var_stop_sec_none_peak INT DEFAULT(0);	 -- 정류장 필요 정차시간(비첨두시)
    DECLARE var_max_speed FLOAT DEFAULT(59);	 -- 최고 속도(km/h)
    DECLARE var_max_speed_per_sec FLOAT DEFAULT(0);	 -- 최고 속도(m/s)
    DECLARE var_max_delay_sec INT DEFAULT(20);	 -- 연장 최대 시간(초)
    
    DECLARE var_acc_avg FLOAT;	 -- 출발 시 평균 가속도(m/s2)
    DECLARE var_dec_avg FLOAT;	 -- 정차 시 평균 감속도(m/s2)
    DECLARE var_acc_avg_tm INT;	 -- 출발 시 평균 가속 시간(sec)
    DECLARE var_dec_avg_tm INT;	 -- 정차 시 평균 감속 시간(sec)
    DECLARE var_drv_avg_tm INT;	 -- 최고속도 주행 시간(sec)
    
    DECLARE var_acc_len INT;	 -- 출발 시 가속 거리(m)
    DECLARE var_dec_len INT;	 -- 도착 시 감속 거리(m)
    DECLARE var_drv_len INT;	 -- 최고속도 주행 거리(m)
    

    -- 오전 첨두시 시작/종료
    DECLARE var_am_peak_st_tm TIME;	
    DECLARE var_am_peak_ed_tm TIME;	    
    -- 오후 첨두시 시작/종료
    DECLARE var_pm_peak_st_tm TIME;	
    DECLARE var_pm_peak_ed_tm TIME;

	DECLARE var_no_more_rows INT DEFAULT(0);   	
    
    
	-- 노선, 노드 정보 가져오기
	DECLARE var_cursor CURSOR FOR	     
	SELECT
		B.REP_ROUT_ID
		, B.ROUT_ID
		, B.DAY_DIV
        , C.WAY_DIV
		, A.NODE_ID
		, A.NODE_SN
		, A.NODE_TYPE
        , B.ALLOC_NO
		, D.LINK_ID
		, E.LEN
		#, (E.LEN / (var_max_speed*1000 / 3600)) AS secs	-- 다음 노드까지 걸리는 시간(초). link 길이(m) / (50km/h / 1시간). 수정 요망
	FROM BMS_ROUT_NODE_CMPSTN A
		LEFT JOIN BRT_OPER_ALLOC_PL_ROUT_INFO B ON A.ROUT_ID = B.ROUT_ID
		LEFT JOIN BMS_ROUT_MST C ON B.ROUT_ID = C.ROUT_ID				
		LEFT JOIN BMS_ROUT_LINK_CMPSTN D ON (A.ROUT_ID = D.ROUT_ID) AND (A.LINK_ID = (D.LINK_ID))
		LEFT JOIN BMS_LINK_MST E ON E.LINK_ID = D.LINK_ID
	WHERE A.ROUT_ID = param_route_id
		AND B.OPER_SN = param_oper_sn
		AND A.LINK_NODE_YN = 'Y'
        AND B.DAY_DIV = param_day_div
	ORDER BY A.NODE_SN ASC; 
       
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET var_no_more_rows = true;
    
    
    # 변수 초기화
    SET var_max_speed = 50; -- 59; -- 60;
    SET var_max_delay_sec = 20;
    #SET var_acc_avg = (1.67*0.8); #1.4;  -- 기준가속(1.67) 의 80%
	#SET var_dec_avg = (-2.5*0.8); #-2.1; -- 기준감속(-2.5) 의 80%
    SET var_acc_avg = 1.4;  -- 기준가속(1.67) 의 80%
	SET var_dec_avg = -2.1; -- 기준감속(-2.5) 의 80%
    
    SET var_max_speed_per_sec = (var_max_speed*1000/3600); # m/s 로 변경
    
		
	-- 운행순번에 따른 기점 출발시각, 종점 도착 시각
	SELECT
		MIN(A.NODE_SN)
		, MAX(A.NODE_SN)
		, ROUT_ST_TM
		, ROUT_ED_TM
		INTO var_first_node_sn, var_last_node_sn, var_route_st_tm, var_route_ed_tm
	FROM BMS_ROUT_NODE_CMPSTN A
		LEFT JOIN BRT_OPER_ALLOC_PL_ROUT_INFO B ON A.ROUT_ID = B.ROUT_ID AND B.OPER_SN = param_oper_sn		
	WHERE A.ROUT_ID = param_route_id AND B.DAY_DIV = param_day_div;
	
	-- 최소 정차시간 가져오기
	SELECT TXT_VAL1 INTO var_min_stop_sec FROM BMS_DL_CD_INFO WHERE CO_CD = 'SYS_INFO' AND DL_CD = 'SY003';	
	-- 최대 정차시간 가져오기 
	SELECT TXT_VAL1 INTO var_max_stop_sec FROM BMS_DL_CD_INFO WHERE CO_CD = 'SYS_INFO' AND DL_CD = 'SY004';
		
	-- 노선의 첨두시 가져오기
	SELECT 
        A.AM_PEAK_ST_TM
        , A.AM_PEAK_ED_TM
        , A.PM_PEAK_ST_TM
        , A.PM_PEAK_ED_TM
        INTO var_am_peak_st_tm, var_am_peak_ed_tm, var_pm_peak_st_tm, var_pm_peak_ed_tm
	FROM BRT_OPER_PL_MST A
		LEFT JOIN BMS_ROUT_MST B ON A.REP_ROUT_ID = B.REP_ROUT_ID
	WHERE B.ROUT_ID = param_route_id
		AND A.DAY_DIV = param_day_div
		AND A.WAY_DIV = B.WAY_DIV;           

    -- 대표노선 가져오기
    SELECT REP_ROUT_ID INTO var_rep_route_id
    FROM BMS_ROUT_MST
    WHERE ROUT_ID = param_route_id;	 

   -- delete before insert
	DELETE FROM BRT_OPER_ALLOC_PL_NODE_INFO
	WHERE REP_ROUT_ID = var_rep_route_id
		AND ROUT_ID = param_route_id
		AND DAY_DIV = param_day_div
        AND OPER_SN = param_oper_sn;    
    
	-- 
	OPEN var_cursor;
	var_loop: LOOP	
	
		FETCH var_cursor 
			INTO var_rep_route_id
	        ,var_route_id
	        ,var_day_div
            ,var_way_div
	        ,var_node_id
	        ,var_node_sn
	        ,var_node_type
            ,var_alloc_no
	        ,var_link_id
            ,var_link_len;
	        #,var_next_diff_sec;		
	
		-- CALL PROC_TMP_DBG(var_node_id);
    
		IF var_no_more_rows THEN							        
			LEAVE var_loop;
		END IF;			        
               
		#################################################################################
		# 2as = v제곱 - v0제곱
        # s = v0t + 1/2at2        
		# v = v0 + at;
        # t = (v-v0)/a    
        
        # 시작속도가 0일 때       
        # t = 루트(2s/a)
        
        # 시작속도가 max일 때       
        
        # 기준가속 1.67, 기준감속 -2.5
		#SET var_acc_avg = 1.4;
		#SET var_dec_avg = -2.1;
        # SET var_acc_avg = 4;
		# SET var_dec_avg = -5;
        
		SET var_acc_avg_tm = (var_max_speed_per_sec-0) / var_acc_avg; # 평균 가속 시간      
		SET var_dec_avg_tm = (0 - var_max_speed_per_sec) / var_dec_avg; #평균 감속 시간
        						
		-- 다음 노드정보 확인
		SELECT B.ED_NODE_ID
			INTO var_next_node_id
		FROM BMS_ROUT_NODE_CMPSTN A
			LEFT JOIN BMS_LINK_MST B ON A.LINK_ID = B.LINK_ID
		WHERE A.ROUT_ID = param_route_id
			AND A.NODE_ID = var_node_id
			AND A.NODE_SN = var_node_sn;
							
		SET var_next_node_sn = var_node_sn + 1;       
        
		SELECT
			A.NODE_TYPE
			,A.CRS_ID
			,B.ENT_PHASE_NO_1
			,B.ENT_PHASE_NO_2
			,B.ENT_PHASE_NO_3
			INTO var_next_node_type, var_next_cross_id, 
					var_enter_phase_no1, var_enter_phase_no2, var_enter_phase_no3
		FROM BMS_ROUT_NODE_CMPSTN A
			LEFT JOIN BMS_ROUT_CRS_CMPSTN B ON A.ROUT_ID = B.ROUT_ID AND A.NODE_ID = B.CRS_ID AND A.NODE_SN = B.NODE_SN
		WHERE A.ROUT_ID = param_route_id AND A.NODE_ID = var_next_node_id AND A.NODE_SN = var_next_node_sn;	        
        
        
        #CALL PROC_TMP_DBG(CONCAT(param_route_id, ',', var_next_node_id, ',', var_next_node_type, ',', var_next_cross_id));
        #CALL PROC_TMP_DBG(CONCAT(var_next_node_type, ',', var_next_cross_id));
        
        
        SET var_acc_len = 0;
		SET var_dec_len = 0;
        
        IF(var_node_type = 'NT002' OR var_node_sn = var_first_node_sn) THEN  #정류장에서 출발하는 경우 or 첫번째 노드에서 출발
			# 가속구간
			SET var_acc_len = 0 + (var_acc_avg*POW(var_acc_avg_tm, 2))/2;
            
            # 가속구간이 링크길이보다 긴 경우
            IF(var_acc_len >= var_link_len) THEN
				SET var_acc_len = var_link_len;
                #SET var_acc_len = 0;
                SET var_acc_avg_tm = SQRT(2*var_acc_len/var_acc_avg);
                
            END IF;
        END IF;
                
		IF(var_next_node_type = 'NT002') THEN  #다음노드가 정류장인 경우
			# 감속구간
            SET var_dec_len = (var_max_speed_per_sec*var_dec_avg_tm) + (var_dec_avg*POW(var_dec_avg_tm, 2))/2;
            
            # 감속구간이 링크길이보다 긴 경우
            IF(var_dec_len >= var_link_len) THEN
				SET var_dec_len = var_link_len;
                #SET var_dec_len = 0;
                
                #SET var_dec_avg = (0 - POW(var_max_speed_per_sec, 2)) / (2*var_dec_len);
                #SET var_dec_avg_tm = SQRT(2*var_dec_len/var_dec_avg);                
                SET var_dec_avg_tm = var_dec_avg_tm/2;
                
            END IF;            
        END IF;
        
        
        # 위 경우를 제외한 나머지 구간은 최고속도로 달린다고 가정
		# 최고속도로 달리는 시간        
		SET var_drv_avg_tm = (var_link_len - var_acc_len - var_dec_len) / var_max_speed_per_sec; 	
        
       
		IF(var_acc_len = 0) THEN
			SET var_acc_avg_tm = 0;
        END IF;
        
        IF(var_dec_len = 0) THEN
			SET var_dec_avg_tm = 0;
        END IF;
        
        # 다음노드까지 걸리는 시간
        # 가속시간 + 감속시간 + 등속주행시간
		SET var_next_diff_sec = var_acc_avg_tm + var_dec_avg_tm + var_drv_avg_tm;   
        #########################################################################################        
        
								

        
		-- 도착시각/출발시각 계산      
		IF(var_node_sn = var_first_node_sn) THEN      -- 노선의 첫번째 노드이면 			
			SET var_dprt_tm = TIME_FORMAT(var_route_st_tm, '%H:%i:%S');
            SET var_arrv_tm = var_dprt_tm; -- 도착시각=출발시각
            
            -- 첫 번째 노드가 정류장이 아닌경우 첫번째 정류장의 출발시각 설정
            IF(var_node_type != 'NT002' AND var_next_node_type = 'NT002') THEN
				SET var_next_diff_sec = 0;
				SET var_prev_diff_sec = 0;
				SET var_prev_dprt_tm = var_dprt_tm;	
            ELSE
				SET var_prev_diff_sec = var_next_diff_sec;
				SET var_prev_dprt_tm = var_dprt_tm;	
            END IF;          												
            
		ELSEIF(var_node_sn = var_last_node_sn AND var_node_type != 'NT002') THEN  -- 노선의 마지막 노드이면        
			
            /*
		 	SET var_arrv_tm = TIME_FORMAT(var_route_ed_tm, '%H:%i:%S');
            SET var_dprt_tm = var_arrv_tm; -- 출발시각=도착시각
            */
            
            # 마지막 노드가 정류장이 아니면
			#IF(var_node_type != 'NT002') THEN
				# 마지막 노드가 정류장이 아니면 이전 노드(정류장)의 시간을 그대로 사용				
				SET var_arrv_tm = var_prev_dprt_tm;
                SET var_dprt_tm = var_arrv_tm; -- 출발시각=도착시각
            #END IF;                      					
            
            

		ELSE        						
        						
			SET var_arrv_tm = DATE_ADD(var_prev_dprt_tm, INTERVAL var_prev_diff_sec SECOND); -- 이전 노드 출발시각 + 현재노드까지 걸리는 시간
			SET var_arrv_tm = DATE_ADD(var_arrv_tm, INTERVAL var_add_arrv_sec SECOND); -- 신호가 걸려 추가된 시간            
			           
			SET var_add_arrv_sec = 0; -- 초기화
			SET var_stop_sec_peak = 0;
			SET var_stop_sec_none_peak = 0;             
      	      	
			-- 노드타입에 따라 최소정차시간 추가
			IF(var_node_type = 'NT002') THEN -- 정류장			      															
               
               -- CALL PROC_TMP_DBG(var_node_id);
               -- CALL PROC_TMP_DBG('정류장');
               
				-- 정류장별 필요정차시간 가져오기
				SELECT STOP_TM_PEAK, STOP_TM_NONE_PEAK 
					INTO var_stop_sec_peak, var_stop_sec_none_peak 
				FROM BMS_STTN_MST WHERE STTN_ID = var_node_id;                
               
                IF(var_stop_sec_peak = 0 OR var_stop_sec_none_peak = 0) THEN -- 데이터가 없는 경우                               
					SET var_dprt_tm = DATE_ADD(var_arrv_tm, INTERVAL var_min_stop_sec SECOND); -- 최소정차시간 추가
                ELSEIF(var_stop_sec_peak IS NOT NULL AND var_stop_sec_none_peak IS NOT NULL) THEN -- 정류장별 필요정차시간 확인
                
					################ 정차시간 조정
					#SET var_stop_sec_peak = var_stop_sec_peak * 1.2;
                    #SET var_stop_sec_none_peak = var_stop_sec_none_peak * 1.2;
                
					IF(var_arrv_tm >= var_am_peak_st_tm && var_arrv_tm <= var_am_peak_ed_tm) THEN	-- 오전첨두시			
						SET var_dprt_tm = DATE_ADD(var_arrv_tm, INTERVAL var_stop_sec_peak SECOND);
					ELSEIF(var_arrv_tm >= var_pm_peak_st_tm && var_arrv_tm <= var_pm_peak_ed_tm) THEN -- 오후첨두시
						SET var_dprt_tm = DATE_ADD(var_arrv_tm, INTERVAL var_stop_sec_peak SECOND);
					else  -- 비첨두시              
						SET var_dprt_tm = DATE_ADD(var_arrv_tm, INTERVAL var_stop_sec_none_peak SECOND);
					END IF;				
				ELSE -- 필요정차시간이 없는 경우
					SET var_dprt_tm = DATE_ADD(var_arrv_tm, INTERVAL var_min_stop_sec SECOND); -- 최소정차시간 추가
                END IF;
                
			ELSE
				SET var_dprt_tm = var_arrv_tm;
			END IF;							
           					            
	      
			SET var_prev_diff_sec = var_next_diff_sec;
            SET var_prev_dprt_tm = var_dprt_tm;
            
            
			-- 마지막 정류장 처리
            /*
			IF(var_next_node_sn = var_last_node_sn) THEN  -- 다음노드가 노선의 마지막 노드이면
				IF(var_node_type = 'NT002' AND var_next_node_type != 'NT002') THEN -- 다음 노드가 정류장이 아니면
					#SET var_arrv_tm = TIME_FORMAT(var_route_ed_tm, '%H:%i:%S');
					#SET var_dprt_tm = var_arrv_tm; -- 출발시각=도착시각	            
                    
					# 계산 해보니 최종 도착시간이 맞지 않다.
					# 도착시간이 빠른 경우
					# 도착시간이 늦는 경우                    
                    
                    
				END IF;  
            END IF;
            */
            
	      	
		END IF;


        
        IF(var_node_sn != var_last_node_sn) THEN 
			IF(var_next_node_type = 'NT001' && var_next_cross_id IS NOT NULL) THEN -- 다음 노드가 교차로이면
            
				-- 현시정보 확인가능여부
                SELECT SIG_CTR_TYPE
					INTO var_sig_ctr_type
				FROM BMS_CRS_MST
				WHERE CRS_ID = var_next_cross_id;     
                
                -- 현시정보 확인이 가능한 경우 [[
                IF(var_sig_ctr_type IS NOT NULL AND var_sig_ctr_type != '') THEN                                                                      
            
					-- 교차로 도착예정 시각의 진입현시 확인
					SELECT FN_GET_PHASE_REMAIN_TM(
							var_next_cross_id, 
							DATE_ADD(var_dprt_tm, INTERVAL var_next_diff_sec SECOND), 
							var_enter_phase_no1,
							IF(var_day_div = 'DY001', 2, 1)  -- 평일, 휴일 체크. 2:월요일(평일), 1:일요일(휴일)
					) INTO var_phase_remain_sec;												                          				
					
					
					-- CALL PROC_TMP_DBG(CONCAT(var_next_cross_id, ',', var_dprt_tm, ',', var_enter_phase_no1, ',', var_phase_remain_sec));
					
							  
					-- 다음 교차로 도착예정 시각에 var_enter_phase_no1 현시가 아닌경우
					-- var_enter_phase_no2 가 있는지 확인
					IF(var_phase_remain_sec <= 0) THEN                      
						SET var_phase_remain_sec_tmp = 0;                   
						
						SELECT FN_GET_PHASE_REMAIN_TM(
								var_next_cross_id, 
								DATE_ADD(var_dprt_tm, INTERVAL var_next_diff_sec SECOND), 
								var_enter_phase_no2,
								IF(var_day_div = 'DY001', 2, 1)  -- 평일, 휴일 체크. 2:월요일(평일), 1:일요일(휴일)
						) INTO var_phase_remain_sec_tmp;                    
						
						IF(var_phase_remain_sec_tmp > 0) THEN
							SET var_phase_remain_sec = var_phase_remain_sec_tmp;         
						END IF;
						
						-- CALL PROC_TMP_DBG(CONCAT(var_next_cross_id, ',', var_dprt_tm, ',', var_enter_phase_no2, ',', var_phase_remain_sec));
						
					END IF;
					
					-- 다음 교차로 도착예정 시각에 var_enter_phase_no2 현시가 아닌경우
					-- var_enter_phase_no3 가 있는지 확인
					IF(var_phase_remain_sec <= 0) THEN                      
						SET var_phase_remain_sec_tmp = 0;   
						
						SELECT FN_GET_PHASE_REMAIN_TM(
								var_next_cross_id, 
								DATE_ADD(var_dprt_tm, INTERVAL var_next_diff_sec SECOND), 
								var_enter_phase_no3,
								IF(var_day_div = 'DY001', 2, 1)  -- 평일, 휴일 체크. 2:월요일(평일), 1:일요일(휴일)
						) INTO var_phase_remain_sec_tmp;                    
						
						IF(var_phase_remain_sec_tmp > 0) THEN
							SET var_phase_remain_sec = var_phase_remain_sec_tmp;
						END IF;
						
						-- CALL PROC_TMP_DBG(CONCAT(var_next_cross_id, ',', var_dprt_tm, ',', var_enter_phase_no3, ',', var_phase_remain_sec));
					END IF;      
                    
				END IF; 
                -- 현시정보 확인이 가능한 경우 ]]
                
			 	
			 	-- 다음 교차로 도착예정 시각에 원하는 현시가 아닌 경우(다음 초록불까지 남은 시간..)
			 	IF(var_phase_remain_sec < 0) THEN
			 		-- 다음 초록불까지 남은 시간에 따라 
			 		-- 현재 노드가 정류장이면 출발시각을 늦춘다(20초 정도까지..)
			 		IF(var_node_type = 'NT002') THEN -- 정류장
			 			IF(ABS(var_phase_remain_sec) > var_max_delay_sec) THEN
							SET var_dprt_tm = DATE_ADD(var_dprt_tm, INTERVAL var_max_delay_sec SECOND);
							# SET var_dprt_tm = var_dprt_tm + var_max_delay_sec;
				 		ELSE
							SET var_dprt_tm = DATE_ADD(var_dprt_tm, INTERVAL ABS(var_phase_remain_sec) SECOND);
					 		# SET var_dprt_tm = var_dprt_tm + ABS(var_phase_remain_sec); 
			 			END IF;
					ELSE 
				 		-- 현재 노드가 정류장이 아닌 경우 다음 노드의 도착시각을 늦춘다??
			 			IF(ABS(var_phase_remain_sec) > var_max_delay_sec) THEN
				 			SET var_add_arrv_sec = var_max_delay_sec;
				 		ELSE
				 			SET var_add_arrv_sec = ABS(var_phase_remain_sec); 
			 			END IF;				 		
			 		END IF;			 		

			 	-- ELSEIF(var_phase_remain_sec > 0) THEN -- 다음 교차로 도착예정 시각에 원하는 현시가 진행 중인 경우(초록불 남은 시간..)
					-- 초록불이 남은 시간에 관계없이 그냥 감
			 		-- 초록불 남은 시간에 따라 도착시각을 당겨야 할 수도 있다
			 		-- SET var_add_arrv_sec  = ABS(var_phase_remain_sec); -- //<<====== 수정 필요
			 	-- 남은시간이 0 인 경우는 ERROR임
			 	END IF;		 			
                
			 	
			ELSE -- 다음노드가 교차로가 아니면
			 	SET var_phase_remain_sec = 0;
			END IF;			      	      
	      
			SET var_prev_diff_sec = var_next_diff_sec;
            SET var_prev_dprt_tm = var_dprt_tm;
        
        END IF;				
      			
		
		INSERT INTO BRT_OPER_ALLOC_PL_NODE_INFO
		(
			REP_ROUT_ID
			,DAY_DIV
			,WAY_DIV
			,ALLOC_NO
			,OPER_SN
			,ROUT_ID			
			,NODE_ID
			-- ,COR_ID
			,NODE_SN
			,NODE_TYPE
			,ARRV_TM
			,DPRT_TM
			,UPD_DTM
			,TEST -- test
			,TEST2 -- test
            ,TEST3 -- test
		)				
		VALUES	
		(
			var_rep_route_id
			,var_day_div 
			,var_way_div
			,var_alloc_no
			,param_oper_sn
			,var_route_id						
			,var_node_id
			-- ,param_course_id -- course id
			,var_node_sn
			,var_node_type
			,var_arrv_tm
			,var_dprt_tm
			, NOW()
            ,var_link_len -- test
			,var_phase_remain_sec #var_acc_len #var_link_len #var_phase_remain_sec -- test
			,var_next_cross_id #var_next_diff_sec #var_dec_len # var_next_diff_sec #var_next_cross_id -- test
		)
		ON DUPLICATE KEY UPDATE		
			 COR_ID = VALUES(COR_ID)
			,NODE_SN = VALUES(NODE_SN)
			,NODE_TYPE = VALUES(NODE_TYPE)
			,ARRV_TM = VALUES(ARRV_TM)            
			,DPRT_TM = VALUES(DPRT_TM)
			,UPD_DTM = VALUES(UPD_DTM)
			,TEST = VALUES(TEST)	-- test	 
			,TEST2 = VALUES(TEST2)	-- test	            
            ,TEST3 = VALUES(TEST3);	-- test	   

        
		SET var_no_more_rows = false;  -- 예상치 않은 loop 빠져나감 방지
      
	END LOOP var_loop;    
    
	CLOSE var_cursor;

END $$
DELIMITER ;;

DELIMITER $$
CREATE OR REPLACE PROCEDURE `sbrt`.`PROC_MAKE_OPER_PL_ROUT_INFO`(
	IN `param_route_id` VARCHAR(10),
	IN `param_day_div` VARCHAR(5)
)
    COMMENT '운행계획노선정보 생성(노선별 운행회차 정보)'
BEGIN

	DECLARE var_rep_route_id VARCHAR(10); -- 대표노선아이디
    DECLARE var_oper_cnt INT;
    DECLARE var_fst_tm TIME;	-- 노선 첫차 출발 시각
	DECLARE var_lst_tm TIME;    -- 노선 막차 출발 시각
	DECLARE var_am_peak_min INT; -- 오전첨두시 배차간격(분)
    DECLARE var_pm_peak_min INT; -- 오후첨두시 배차간격(분)
    DECLARE var_none_peak_min INT; -- 비첨두시 배차간격(분) 
    DECLARE var_route_run_sec INT; -- 노선을 운행하는데 걸리는 시간(초).
    
	DECLARE var_min_stop_sec INT;	 -- 최소 정차시간
    
    DECLARE var_oper_sn INT DEFAULT(0);	
    DECLARE var_rout_st_tm TIME;	-- 노선 기점 출발시각
    DECLARE var_rout_ed_tm TIME;    -- 노선 종점 도착시각
        
    -- 오전 첨두시 시작/종료
    DECLARE var_am_peak_st_tm TIME;	
    DECLARE var_am_peak_ed_tm TIME;	    
    -- 오후 첨두시 시작/종료
    DECLARE var_pm_peak_st_tm TIME;	
    DECLARE var_pm_peak_ed_tm TIME;
    
    DECLARE var_max_speed INT DEFAULT(50);	 -- 최고 속도(km/h)
    DECLARE var_route_cnt INT DEFAULT(1);   -- 동일 대표노선을 가지는 노선 개수
    DECLARE var_station_cnt INT DEFAULT(1);   -- 해당 노선에 있는 정류장 개수
    
    DECLARE var_break_loop INT DEFAULT(0);
    
    
    -- 오전 첨두시 가져오기
    /* -- 노선별 첨두시간은 BRT_OPER_PL_MST 에 있음
    SELECT TIME_FORMAT(TXT_VAL1, '%H:%i:%S'), TIME_FORMAT(TXT_VAL2, '%H:%i:%S') 
		INTO var_am_peak_st_tm, var_am_peak_ed_tm 
    FROM BMS_DL_CD_INFO 
    WHERE CO_CD = 'PEAK_TIME' AND DL_CD = 'PT001';	
    
    -- 오후 첨두시 가져오기
    SELECT TIME_FORMAT(TXT_VAL1, '%H:%i:%S'), TIME_FORMAT(TXT_VAL2, '%H:%i:%S') 
		INTO var_pm_peak_st_tm, var_pm_peak_ed_tm
    FROM BMS_DL_CD_INFO 
    WHERE CO_CD = 'PEAK_TIME' AND DL_CD = 'PT002';	
    */
    	
	-- 최소 정차시간 가져오기
	SELECT TXT_VAL1 INTO var_min_stop_sec FROM BMS_DL_CD_INFO WHERE CO_CD = 'SYS_INFO' AND DL_CD = 'SY003';	        
        
	-- 노선에 있는 정류장 개수
	SELECT COUNT(NODE_ID) AS cnt
		INTO var_station_cnt
	FROM BMS_ROUT_NODE_CMPSTN
    WHERE ROUT_ID = param_route_id AND NODE_TYPE = 'NT002';        
        
        
	-- 노선 운행정보
	SELECT 
		A.REP_ROUT_ID
		, TIME_FORMAT(A.FST_TM, '%H:%i:%S')
		, TIME_FORMAT(A.LST_TM, '%H:%i:%S')
		, A.AM_PEAK
		, A.PM_PEAK
		, A.NONE_PEAK
        #, A.AM_PEAK_ST_TM
        #, A.AM_PEAK_ED_TM
        #, A.PM_PEAK_ST_TM
        #, A.PM_PEAK_ED_TM
        ,  (B.ROUT_LEN / ( var_max_speed*1000 / 3600)) AS secs
        INTO var_rep_route_id, var_fst_tm, var_lst_tm, var_am_peak_min, var_pm_peak_min, var_none_peak_min
			#, var_am_peak_st_tm, var_am_peak_ed_tm, var_pm_peak_st_tm, var_pm_peak_ed_tm
            , var_route_run_sec
	FROM BRT_OPER_PL_MST A
		LEFT JOIN BMS_ROUT_MST B ON A.ROUT_ID = B.ROUT_ID
	WHERE A.ROUT_ID = param_route_id
	AND A.DAY_DIV = param_day_div;
    
    -- 동일 대표노선을 가지는 노선 개수 -- 어디에 쓸 수 있을까...?
    SELECT COUNT(ROUT_ID) INTO var_route_cnt FROM BMS_ROUT_MST WHERE REP_ROUT_ID = var_rep_route_id;


	-- delete before insert
	DELETE FROM BRT_OPER_PL_ROUT_INFO
	WHERE REP_ROUT_ID = var_rep_route_id
		AND ROUT_ID = param_route_id
		AND DAY_DIV = param_day_div;
    
    
    SET var_oper_sn = 0;
    loop_oper_pl: LOOP							
                
		SET var_oper_sn = var_oper_sn+1; -- 회차 증가
        
        IF(var_oper_sn = 1) THEN   -- 1회차
			SET var_rout_st_tm = var_fst_tm;
		ELSE
			IF(var_rout_st_tm >= var_am_peak_st_tm AND var_rout_st_tm <= var_am_peak_ed_tm) THEN				
				SET var_rout_st_tm = DATE_ADD(var_rout_st_tm, INTERVAL var_am_peak_min MINUTE); -- 오전첨두시 배차간격
			ELSEIF(var_rout_st_tm >= var_pm_peak_st_tm AND var_rout_st_tm <= var_pm_peak_ed_tm) THEN
                SET var_rout_st_tm = DATE_ADD(var_rout_st_tm, INTERVAL var_pm_peak_min MINUTE); -- 오후첨두시 배차간격
			else                
                SET var_rout_st_tm = DATE_ADD(var_rout_st_tm, INTERVAL var_none_peak_min MINUTE); -- 비첨두시 배차간격
			END IF;
        END IF;
                
		-- 정류장별 최소정차시간 추가(기점, 종점 제외)
		SET var_route_run_sec = var_route_run_sec + (var_min_stop_sec * (var_station_cnt-2));
        
        SET var_rout_ed_tm = DATE_ADD(var_rout_st_tm, INTERVAL var_route_run_sec SECOND);         
        
        
        IF(var_rout_st_tm >= var_lst_tm) THEN
			SET var_oper_cnt = var_oper_sn-1;
			LEAVE loop_oper_pl;
		END IF;   
		                       
        
        -- insert into BRT_OPER_PL_ROUT_INFO
        /*
        INSERT INTO BRT_OPER_PL_ROUT_INFO
		(
			REP_ROUT_ID
			, ROUT_ID
			, DAY_DIV
			, OPER_SN
			, ROUT_ST_TM
			, ROUT_ED_TM
            , UPD_DTM
		)				
		VALUES	
		(
			var_rep_route_id
			,param_route_id
			,param_day_div
			,var_oper_sn
			,var_rout_st_tm
			,var_rout_ed_tm
			, NOW()
		);
        */
       
		
	END LOOP loop_oper_pl;   
    
    
	-- update BRT_OPER_PL_MST
    /*
	UPDATE BRT_OPER_PL_MST
	SET
		OPER_CNT = var_oper_cnt
		, UPD_DTM = NOW()
	WHERE REP_ROUT_ID = var_rep_route_id
		AND ROUT_ID = param_route_id
		AND DAY_DIV = param_day_div;       
    */



END $$
DELIMITER ;;

DELIMITER $$
CREATE OR REPLACE PROCEDURE `sbrt`.`PROC_MAKE_ROUT_LINK_STAT`()
    COMMENT '노선별 링크 통계 생성'
BEGIN
# DELETE FROM BRT_ROUT_LINK_STAT;

INSERT INTO BRT_ROUT_LINK_STAT
(OPER_DT, REP_ROUT_ID, ROUT_ID, LINK_ID, STAT_H, LINK_SN, LINK_AVRG_SPD
, LINK_MAX_SPD, LINK_MIN_SPD, UPD_DTM)

SELECT
	A.OPER_DT
	, A.REP_ROUT_ID
	, A.ROUT_ID
	, A.LINK_ID
	, A.STAT_H
	, B.LINK_SN
	, A.LINK_AVRG_SPD
	, A.LINK_MAX_SPD
	, A.LINK_MIN_SPD
    , NOW()
FROM
	(SELECT
		H.OPER_DT
		, H.REP_ROUT_ID
		, H.ROUT_ID
		, H.LINK_ID
		-- , CONCAT(LEFT(H.UPD_DTM, 13), ':00:00') AS STAT_DTM
        , SUBSTR(H.UPD_DTM, 12, 2) AS STAT_H
		, null AS LINK_SN
		#, AVG(CUR_SPD) AS LINK_AVRG_SPD
		#, MAX(CUR_SPD) AS LINK_MAX_SPD
		#, MIN(CUR_SPD) AS LINK_MIN_SPD
		#, ((SELECT C.LINK_AVRG_SPD FROM BRT_ROUT_LINK_STAT C WHERE C.LINK_ID = H.LINK_ID ORDER BY C.STAT_H ASC LIMIT 1) + AVG(H.CUR_SPD))/2 AS LINK_AVRG_SPD
		, AVG(H.CUR_SPD) AS LINK_AVRG_SPD        
		, MAX(H.CUR_SPD) AS LINK_MAX_SPD
		, MIN(H.CUR_SPD) AS LINK_MIN_SPD
	FROM BRT_CUR_OPER_HIS H
		LEFT JOIN BRT_ROUT_LINK_STAT S ON H.LINK_ID = S.LINK_ID
		
	WHERE H.LINK_ID IS NOT NULL		
		AND H.UPD_DTM > DATE_ADD(NOW(), INTERVAL -1 HOUR)	
	GROUP BY H.OPER_DT, H.REP_ROUT_ID, H.ROUT_ID, H.LINK_ID, (LEFT(H.UPD_DTM, 13))
    ) A
    LEFT JOIN
    BMS_ROUT_LINK_CMPSTN B ON A.ROUT_ID = B.ROUT_ID AND A.LINK_ID = B.LINK_ID
    
	ON DUPLICATE KEY UPDATE
		LINK_SN		= VALUES(LINK_SN)
		, LINK_AVRG_SPD	= VALUES(LINK_AVRG_SPD)
		, LINK_MAX_SPD	= VALUES(LINK_MAX_SPD)
		, LINK_MIN_SPD	= VALUES(LINK_MIN_SPD)
		, UPD_DTM		= VALUES(UPD_DTM);        

END $$
DELIMITER ;;

DELIMITER $$
CREATE OR REPLACE PROCEDURE `sbrt`.`PROC_TMP_DBG`(
	IN `msg` VARCHAR(500)
)
BEGIN
	INSERT INTO TMP_LOG VALUES( (SELECT seq 
									FROM (SELECT IFNULL(MAX(seq),0)+1 AS seq
                                                      FROM TMP_LOG
                                         ) A
                                )
								,msg
								,NOW());
END $$
DELIMITER ;;
