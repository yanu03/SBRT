-- 트리거 sbrt.BMS_CRS_GRP_SIGOPER_INFO_after_update 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `BMS_CRS_GRP_SIGOPER_INFO_after_update` AFTER UPDATE ON `BMS_CRS_GRP_SIGOPER_INFO` FOR EACH ROW BEGIN

	INSERT INTO BMS_CRS_GRP_SIGOPER_HIS(
			BMS_CRS_GRP_SIGOPER_HIS.UPD_DTM
			,BMS_CRS_GRP_SIGOPER_HIS.CRS_GRP_ID
			,BMS_CRS_GRP_SIGOPER_HIS.SIG_CTR_STS
			,BMS_CRS_GRP_SIGOPER_HIS.SIG_OPER_MODE
			,BMS_CRS_GRP_SIGOPER_HIS.PHASE_LEN
			,BMS_CRS_GRP_SIGOPER_HIS.PERIOD_CNT
			,BMS_CRS_GRP_SIGOPER_HIS.DAY_PL_NO
			,BMS_CRS_GRP_SIGOPER_HIS.PHASE_PL_NO
			,BMS_CRS_GRP_SIGOPER_HIS.LINK_PL_NO)
	VALUE(
			NEW.UPD_DTM
			,NEW.CRS_GRP_ID
			,NEW.SIG_CTR_STS
			,NEW.SIG_OPER_MODE
			,NEW.PHASE_LEN
			,NEW.PERIOD_CNT
			,NEW.DAY_PL_NO
			,NEW.PHASE_PL_NO
			,NEW.LINK_PL_NO
	);

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 트리거 sbrt.BMS_CRS_MST_after_update 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `BMS_CRS_MST_after_update` AFTER UPDATE ON `BMS_CRS_MST` FOR EACH ROW BEGIN
	INSERT INTO BMS_CRS_HIS(
			BMS_CRS_HIS.UPD_DTM
			,BMS_CRS_HIS.CRS_ID
			,BMS_CRS_HIS.CRS_NM
			,BMS_CRS_HIS.GPS_X
			,BMS_CRS_HIS.GPS_Y
			,BMS_CRS_HIS.CRS_KIND
			,BMS_CRS_HIS.SIG_CTR_TYPE
			,BMS_CRS_HIS.MAIN_PHASE
			,BMS_CRS_HIS.PHASE_CNT
			,BMS_CRS_HIS.PERIOD_LEN
			,BMS_CRS_HIS.PERIOD_CNT
			,BMS_CRS_HIS.APPL_ST_DT
			,BMS_CRS_HIS.APPL_ED_DT
			,BMS_CRS_HIS.REMARK
			,BMS_CRS_HIS.USE_YN
			,BMS_CRS_HIS.DEL_YN
			,BMS_CRS_HIS.UPD_ID)
	VALUE(
			NEW.UPD_DTM
			,NEW.CRS_ID
			,NEW.CRS_NM
			,NEW.GPS_X
			,NEW.GPS_Y
			,NEW.CRS_KIND
			,NEW.SIG_CTR_TYPE
			,NEW.MAIN_PHASE
			,NEW.PHASE_CNT
			,NEW.PERIOD_LEN
			,NEW.PERIOD_CNT
			,NEW.APPL_ST_DT
			,NEW.APPL_ED_DT
			,NEW.REMARK
			,NEW.USE_YN
			,NEW.DEL_YN
			,NEW.UPD_ID
	);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 트리거 sbrt.BMS_CRS_SIGOPER_DTL_INFO_after_update 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `sbrt`.`BMS_CRS_SIGOPER_DTL_INFO_after_update` AFTER UPDATE ON `sbrt`.`BMS_CRS_SIGOPER_DTL_INFO`
  FOR EACH ROW
BEGIN
INSERT INTO BMS_CRS_SIGOPER_DTL_HIS(
			BMS_CRS_SIGOPER_DTL_HIS.CRS_ID
			,BMS_CRS_SIGOPER_DTL_HIS.UPD_DTM
			,BMS_CRS_SIGOPER_DTL_HIS.SIG_CTR_TYPE
			,BMS_CRS_SIGOPER_DTL_HIS.SIG_CTR_OPER_DTM
			,BMS_CRS_SIGOPER_DTL_HIS.PWR_STS
			,BMS_CRS_SIGOPER_DTL_HIS.MCU_STS
			,BMS_CRS_SIGOPER_DTL_HIS.DIMMING_STS
			,BMS_CRS_SIGOPER_DTL_HIS.RING_MODE
			,BMS_CRS_SIGOPER_DTL_HIS.PPC_STS
			,BMS_CRS_SIGOPER_DTL_HIS.CTR_MODE
			,BMS_CRS_SIGOPER_DTL_HIS.A_PHASE_NO
			,BMS_CRS_SIGOPER_DTL_HIS.A_STEP_NO
			,BMS_CRS_SIGOPER_DTL_HIS.B_PHASE_NO
			,BMS_CRS_SIGOPER_DTL_HIS.B_STEP_NO
			,BMS_CRS_SIGOPER_DTL_HIS.PP_MNL_PRCS_YN
			,BMS_CRS_SIGOPER_DTL_HIS.PP_MNL_YN
			,BMS_CRS_SIGOPER_DTL_HIS.PP_FLASH_YN
			,BMS_CRS_SIGOPER_DTL_HIS.PP_LIGHT_YN
			,BMS_CRS_SIGOPER_DTL_HIS.CONFLICT_STS
			,BMS_CRS_SIGOPER_DTL_HIS.LIGHT_STS
			,BMS_CRS_SIGOPER_DTL_HIS.BLINK_STS
			,BMS_CRS_SIGOPER_DTL_HIS.DB_STS
			,BMS_CRS_SIGOPER_DTL_HIS.PUSHBTN_YN
			,BMS_CRS_SIGOPER_DTL_HIS.FLASH_REASON
			,BMS_CRS_SIGOPER_DTL_HIS.LTURN_YN
			,BMS_CRS_SIGOPER_DTL_HIS.MNL_YN
			,BMS_CRS_SIGOPER_DTL_HIS.CONFLICT_YN
			,BMS_CRS_SIGOPER_DTL_HIS.DOOR_STS
			,BMS_CRS_SIGOPER_DTL_HIS.CONFLICT_LSU_NO
			,BMS_CRS_SIGOPER_DTL_HIS.CONFLICT_EVID
			,BMS_CRS_SIGOPER_DTL_HIS.CONFLICT_CIRCUIT_NO
			,BMS_CRS_SIGOPER_DTL_HIS.PED_LIGHT_STS
			,BMS_CRS_SIGOPER_DTL_HIS.PUSHBTN_STS
			,BMS_CRS_SIGOPER_DTL_HIS.PED_DEV_STS
			,BMS_CRS_SIGOPER_DTL_HIS.OPT_STS
			,BMS_CRS_SIGOPER_DTL_HIS.PERIOD_CNTR
			,BMS_CRS_SIGOPER_DTL_HIS.PREV_PERIOD_LEN
			,BMS_CRS_SIGOPER_DTL_HIS.PERIOD_LEN
			,BMS_CRS_SIGOPER_DTL_HIS.OFFSET
			,BMS_CRS_SIGOPER_DTL_HIS.PHASE_HOLD_NO
			,BMS_CRS_SIGOPER_DTL_HIS.PHASE_OMIT_NO
			,BMS_CRS_SIGOPER_DTL_HIS.LIGHT_TYPE
			,BMS_CRS_SIGOPER_DTL_HIS.MAP_NO
			,BMS_CRS_SIGOPER_DTL_HIS.SPILL_BACK_STS
			,BMS_CRS_SIGOPER_DTL_HIS.MODULE_STS
			,BMS_CRS_SIGOPER_DTL_HIS.A_PSIG_CTR_STS
			,BMS_CRS_SIGOPER_DTL_HIS.A_PSIG_CTR_MODE
			,BMS_CRS_SIGOPER_DTL_HIS.A_PSIG_PERIOD
			,BMS_CRS_SIGOPER_DTL_HIS.A_VAL
			,BMS_CRS_SIGOPER_DTL_HIS.B_VAL)
	VALUE(
			NEW.CRS_ID
			,UPD_DTM
			,NEW.SIG_CTR_TYPE
			,NEW.SIG_CTR_OPER_DTM
			,NEW.PWR_STS
			,NEW.MCU_STS
			,NEW.DIMMING_STS
			,NEW.RING_MODE
			,NEW.PPC_STS
			,NEW.CTR_MODE
			,NEW.A_PHASE_NO
			,NEW.A_STEP_NO
			,NEW.B_PHASE_NO
			,NEW.B_STEP_NO
			,NEW.PP_MNL_PRCS_YN
			,NEW.PP_MNL_YN
			,NEW.PP_FLASH_YN
			,NEW.PP_LIGHT_YN
			,NEW.CONFLICT_STS
			,NEW.LIGHT_STS
			,NEW.BLINK_STS
			,NEW.DB_STS
			,NEW.PUSHBTN_YN
			,NEW.FLASH_REASON
			,NEW.LTURN_YN
			,NEW.MNL_YN
			,NEW.CONFLICT_YN
			,NEW.DOOR_STS
			,NEW.CONFLICT_LSU_NO
			,NEW.CONFLICT_EVID
			,NEW.CONFLICT_CIRCUIT_NO
			,NEW.PED_LIGHT_STS
			,NEW.PUSHBTN_STS
			,NEW.PED_DEV_STS
			,NEW.OPT_STS
			,NEW.PERIOD_CNTR
			,NEW.PREV_PERIOD_LEN
			,NEW.PERIOD_LEN
			,NEW.OFFSET
			,NEW.PHASE_HOLD_NO
			,NEW.PHASE_OMIT_NO
			,NEW.LIGHT_TYPE
			,NEW.MAP_NO
			,NEW.SPILL_BACK_STS
			,NEW.MODULE_STS
			,NEW.A_PSIG_CTR_STS
			,NEW.A_PSIG_CTR_MODE
			,NEW.A_PSIG_PERIOD
			,NEW.A_VAL
			,NEW.B_VAL
				);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 트리거 sbrt.BMS_CRS_SIGOPER_PHASE_INFO_after_update 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `sbrt`.`BMS_CRS_SIGOPER_PHASE_INFO_after_update` AFTER UPDATE ON `sbrt`.`BMS_CRS_SIGOPER_PHASE_INFO`
  FOR EACH ROW
BEGIN
INSERT INTO BMS_CRS_SIGOPER_PHASE_INFO_HIS(
			BMS_CRS_SIGOPER_PHASE_INFO_HIS.UPD_DTM
			,BMS_CRS_SIGOPER_PHASE_INFO_HIS.CRS_ID
			,BMS_CRS_SIGOPER_PHASE_INFO_HIS.CTR_STS
			,BMS_CRS_SIGOPER_PHASE_INFO_HIS.CTR_MODE
			,BMS_CRS_SIGOPER_PHASE_INFO_HIS.A_PHASE_NO
			,BMS_CRS_SIGOPER_PHASE_INFO_HIS.A_PHASE_TM
			,BMS_CRS_SIGOPER_PHASE_INFO_HIS.B_PHASE_NO
			,BMS_CRS_SIGOPER_PHASE_INFO_HIS.B_PHASE_TM
			)
	VALUE(
			NEW.UPD_DTM
			,NEW.CRS_ID
			,NEW.CTR_STS
			,NEW.CTR_MODE
			,NEW.A_PHASE_NO
			,NEW.A_PHASE_TM
			,NEW.B_PHASE_NO
			,NEW.B_PHASE_TM
	);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 트리거 sbrt.BMS_CRS_SIGOPER_TRF_INFO_after_update 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `sbrt`.`BMS_CRS_SIGOPER_TRF_INFO_after_update` AFTER UPDATE ON `sbrt`.`BMS_CRS_SIGOPER_TRF_INFO`
  FOR EACH ROW
BEGIN
INSERT INTO BMS_CRS_SIGOPER_TRF_HIS(
			BMS_CRS_SIGOPER_TRF_HIS.UPD_DTM
			,BMS_CRS_SIGOPER_TRF_HIS.CRS_ID
			,BMS_CRS_SIGOPER_TRF_HIS.CRS_STS
			,BMS_CRS_SIGOPER_TRF_HIS.CRS_GRP_ID
			,BMS_CRS_SIGOPER_TRF_HIS.PHASE_CNT
			,BMS_CRS_SIGOPER_TRF_HIS.MAIN_PHASE_NO
			,BMS_CRS_SIGOPER_TRF_HIS.PHASE_LEN
			,BMS_CRS_SIGOPER_TRF_HIS.LINK_PL_VAL
			,BMS_CRS_SIGOPER_TRF_HIS.A1_PL
			,BMS_CRS_SIGOPER_TRF_HIS.A2_PL
			,BMS_CRS_SIGOPER_TRF_HIS.A3_PL
			,BMS_CRS_SIGOPER_TRF_HIS.A4_PL
			,BMS_CRS_SIGOPER_TRF_HIS.A5_PL
			,BMS_CRS_SIGOPER_TRF_HIS.A6_PL
			,BMS_CRS_SIGOPER_TRF_HIS.A7_PL
			,BMS_CRS_SIGOPER_TRF_HIS.A8_PL
			,BMS_CRS_SIGOPER_TRF_HIS.B1_PL
			,BMS_CRS_SIGOPER_TRF_HIS.B2_PL
			,BMS_CRS_SIGOPER_TRF_HIS.B3_PL
			,BMS_CRS_SIGOPER_TRF_HIS.B4_PL
			,BMS_CRS_SIGOPER_TRF_HIS.B5_PL
			,BMS_CRS_SIGOPER_TRF_HIS.B6_PL
			,BMS_CRS_SIGOPER_TRF_HIS.B7_PL
			,BMS_CRS_SIGOPER_TRF_HIS.B8_PL
			,BMS_CRS_SIGOPER_TRF_HIS.A1_TRF
			,BMS_CRS_SIGOPER_TRF_HIS.A2_TRF
			,BMS_CRS_SIGOPER_TRF_HIS.A3_TRF
			,BMS_CRS_SIGOPER_TRF_HIS.A4_TRF
			,BMS_CRS_SIGOPER_TRF_HIS.A5_TRF
			,BMS_CRS_SIGOPER_TRF_HIS.A6_TRF
			,BMS_CRS_SIGOPER_TRF_HIS.A7_TRF
			,BMS_CRS_SIGOPER_TRF_HIS.A8_TRF
			,BMS_CRS_SIGOPER_TRF_HIS.B1_TRF
			,BMS_CRS_SIGOPER_TRF_HIS.B2_TRF
			,BMS_CRS_SIGOPER_TRF_HIS.B3_TRF
			,BMS_CRS_SIGOPER_TRF_HIS.B4_TRF
			,BMS_CRS_SIGOPER_TRF_HIS.B5_TRF
			,BMS_CRS_SIGOPER_TRF_HIS.B6_TRF
			,BMS_CRS_SIGOPER_TRF_HIS.B7_TRF
			,BMS_CRS_SIGOPER_TRF_HIS.B8_TRF
			,BMS_CRS_SIGOPER_TRF_HIS.A1_SPD
			,BMS_CRS_SIGOPER_TRF_HIS.A1_LEN
			,BMS_CRS_SIGOPER_TRF_HIS.A2_SPD
			,BMS_CRS_SIGOPER_TRF_HIS.A2_LEN
			,BMS_CRS_SIGOPER_TRF_HIS.A3_SPD
			,BMS_CRS_SIGOPER_TRF_HIS.A3_LEN
			,BMS_CRS_SIGOPER_TRF_HIS.A4_SPD
			,BMS_CRS_SIGOPER_TRF_HIS.A4_LEN
			,BMS_CRS_SIGOPER_TRF_HIS.A5_SPD
			,BMS_CRS_SIGOPER_TRF_HIS.A5_LEN
			,BMS_CRS_SIGOPER_TRF_HIS.A6_SPD
			,BMS_CRS_SIGOPER_TRF_HIS.A6_LEN
			,BMS_CRS_SIGOPER_TRF_HIS.A7_SPD
			,BMS_CRS_SIGOPER_TRF_HIS.A7_LEN
			,BMS_CRS_SIGOPER_TRF_HIS.A8_SPD
			,BMS_CRS_SIGOPER_TRF_HIS.A8_LEN
			,BMS_CRS_SIGOPER_TRF_HIS.B1_SPD
			,BMS_CRS_SIGOPER_TRF_HIS.B1_LEN
			,BMS_CRS_SIGOPER_TRF_HIS.B2_SPD
			,BMS_CRS_SIGOPER_TRF_HIS.B2_LEN
			,BMS_CRS_SIGOPER_TRF_HIS.B3_SPD
			,BMS_CRS_SIGOPER_TRF_HIS.B3_LEN
			,BMS_CRS_SIGOPER_TRF_HIS.B4_SPD
			,BMS_CRS_SIGOPER_TRF_HIS.B4_LEN
			,BMS_CRS_SIGOPER_TRF_HIS.B5_SPD
			,BMS_CRS_SIGOPER_TRF_HIS.B5_LEN
			,BMS_CRS_SIGOPER_TRF_HIS.B6_SPD
			,BMS_CRS_SIGOPER_TRF_HIS.B6_LEN
			,BMS_CRS_SIGOPER_TRF_HIS.B7_SPD
			,BMS_CRS_SIGOPER_TRF_HIS.B7_LEN
			,BMS_CRS_SIGOPER_TRF_HIS.B8_SPD
			,BMS_CRS_SIGOPER_TRF_HIS.B8_LEN
			)
	VALUE(
			NEW.UPD_DTM
			,NEW.CRS_ID
			,NEW.CRS_STS
			,NEW.CRS_GRP_ID
			,NEW.PHASE_CNT
			,NEW.MAIN_PHASE_NO
			,NEW.PHASE_LEN
			,NEW.LINK_PL_VAL
			,NEW.A1_PL
			,NEW.A2_PL
			,NEW.A3_PL
			,NEW.A4_PL
			,NEW.A5_PL
			,NEW.A6_PL
			,NEW.A7_PL
			,NEW.A8_PL
			,NEW.B1_PL
			,NEW.B2_PL
			,NEW.B3_PL
			,NEW.B4_PL
			,NEW.B5_PL
			,NEW.B6_PL
			,NEW.B7_PL
			,NEW.B8_PL
			,NEW.A1_TRF
			,NEW.A2_TRF
			,NEW.A3_TRF
			,NEW.A4_TRF
			,NEW.A5_TRF
			,NEW.A6_TRF
			,NEW.A7_TRF
			,NEW.A8_TRF
			,NEW.B1_TRF
			,NEW.B2_TRF
			,NEW.B3_TRF
			,NEW.B4_TRF
			,NEW.B5_TRF
			,NEW.B6_TRF
			,NEW.B7_TRF
			,NEW.B8_TRF
			,NEW.A1_SPD
			,NEW.A1_LEN
			,NEW.A2_SPD
			,NEW.A2_LEN
			,NEW.A3_SPD
			,NEW.A3_LEN
			,NEW.A4_SPD
			,NEW.A4_LEN
			,NEW.A5_SPD
			,NEW.A5_LEN
			,NEW.A6_SPD
			,NEW.A6_LEN
			,NEW.A7_SPD
			,NEW.A7_LEN
			,NEW.A8_SPD
			,NEW.A8_LEN
			,NEW.B1_SPD
			,NEW.B1_LEN
			,NEW.B2_SPD
			,NEW.B2_LEN
			,NEW.B3_SPD
			,NEW.B3_LEN
			,NEW.B4_SPD
			,NEW.B4_LEN
			,NEW.B5_SPD
			,NEW.B5_LEN
			,NEW.B6_SPD
			,NEW.B6_LEN
			,NEW.B7_SPD
			,NEW.B7_LEN
			,NEW.B8_SPD
			,NEW.B8_LEN
	);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 트리거 sbrt.BMS_LINK_MST_after_update 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `BMS_LINK_MST_after_update` AFTER UPDATE ON `BMS_LINK_MST` FOR EACH ROW BEGIN

INSERT INTO BMS_LINK_HIS(
			BMS_LINK_HIS.UPD_DTM
			,BMS_LINK_HIS.LINK_ID
			,BMS_LINK_HIS.LINK_NM
			,BMS_LINK_HIS.LINK_ENM
			,BMS_LINK_HIS.ST_NODE_ID
			,BMS_LINK_HIS.ED_NODE_ID
			,BMS_LINK_HIS.LINK_SECTION_TYPE
			,BMS_LINK_HIS.LINK_TYPE
			,BMS_LINK_HIS.DIR
			,BMS_LINK_HIS.LEN
			,BMS_LINK_HIS.LINE_CNT
			,BMS_LINK_HIS.GATE_N1
			,BMS_LINK_HIS.GATE_N2
			,BMS_LINK_HIS.ROAD_NM
			,BMS_LINK_HIS.CONN_CD
			,BMS_LINK_HIS.MAX_SPD
			,BMS_LINK_HIS.AVRG_SPD
			,BMS_LINK_HIS.USE_YN
			,BMS_LINK_HIS.SBRT_YN
			,BMS_LINK_HIS.UPD_ID)
	VALUE(
			NOW(3)
			,NEW.LINK_ID
			,NEW.LINK_NM
			,NEW.LINK_ENM
			,NEW.ST_NODE_ID
			,NEW.ED_NODE_ID
			,NEW.LINK_SECTION_TYPE
			,NEW.LINK_TYPE
			,NEW.DIR
			,NEW.LEN
			,NEW.LINE_CNT
			,NEW.GATE_N1
			,NEW.GATE_N2
			,NEW.ROAD_NM
			,NEW.CONN_CD
			,NEW.MAX_SPD
			,NEW.AVRG_SPD
			,NEW.USE_YN
			,NEW.SBRT_YN
			,NEW.UPD_ID
	);

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 트리거 sbrt.BMS_ROUT_MST_after_update 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `BMS_ROUT_MST_after_update` AFTER UPDATE ON `BMS_ROUT_MST` FOR EACH ROW BEGIN
INSERT INTO BMS_ROUT_HIS(
			BMS_ROUT_HIS.UPD_DTM
			,BMS_ROUT_HIS.ROUT_ID
			,BMS_ROUT_HIS.REP_ROUT_ID
			,BMS_ROUT_HIS.INT_ROUT_ID
			,BMS_ROUT_HIS.REP_ROUT_NM
			,BMS_ROUT_HIS.ROUT_NM
			,BMS_ROUT_HIS.ROUT_TYPE
			,BMS_ROUT_HIS.ROUT_DIV
			,BMS_ROUT_HIS.DVC_NM
			,BMS_ROUT_HIS.RSV_ROUT_YN
			,BMS_ROUT_HIS.ST_STTN_ID
			,BMS_ROUT_HIS.ST_STTN_NM
			,BMS_ROUT_HIS.ST_STTN_ENM
			,BMS_ROUT_HIS.ED_STTN_ID
			,BMS_ROUT_HIS.ED_STTN_ENM
			,BMS_ROUT_HIS.ED_STTN_NM
			,BMS_ROUT_HIS.RET_STTN_ID
			,BMS_ROUT_HIS.RET_STTN_NM
			,BMS_ROUT_HIS.RET_STTN_ENM
			,BMS_ROUT_HIS.STTN_CNT
			,BMS_ROUT_HIS.ROUT_LEN
			,BMS_ROUT_HIS.ROUT_STRT_LEN
			,BMS_ROUT_HIS.CURVATURE
			,BMS_ROUT_HIS.JIT_DSPCH_YN
			,BMS_ROUT_HIS.LIC_VHC_CNT
			,BMS_ROUT_HIS.SPR_VHC_CNT
			,BMS_ROUT_HIS.APPL_ST_DT
			,BMS_ROUT_HIS.APPL_ED_DT
			,BMS_ROUT_HIS.WAY_DIV
			,BMS_ROUT_HIS.USER_WAY_DIV
			,BMS_ROUT_HIS.WAY_INFO
			,BMS_ROUT_HIS.AREA
			,BMS_ROUT_HIS.SHAPE_DIV
			,BMS_ROUT_HIS.HOLI_YN
			,BMS_ROUT_HIS.USE_YN
			,BMS_ROUT_HIS.DEL_YN
			,BMS_ROUT_HIS.REMARK
			,BMS_ROUT_HIS.UPD_ID)
	VALUE(
			NEW.UPD_DTM
			,NEW.ROUT_ID
			,NEW.REP_ROUT_ID
			,NEW.INT_ROUT_ID
			,NEW.REP_ROUT_NM
			,NEW.ROUT_NM
			,NEW.ROUT_TYPE
			,NEW.ROUT_DIV
			,NEW.DVC_NM
			,NEW.RSV_ROUT_YN
			,NEW.ST_STTN_ID
			,NEW.ST_STTN_NM
			,NEW.ST_STTN_ENM
			,NEW.ED_STTN_ID
			,NEW.ED_STTN_ENM
			,NEW.ED_STTN_NM
			,NEW.RET_STTN_ID
			,NEW.RET_STTN_NM
			,NEW.RET_STTN_ENM
			,NEW.STTN_CNT
			,NEW.ROUT_LEN
			,NEW.ROUT_STRT_LEN
			,NEW.CURVATURE
			,NEW.JIT_DSPCH_YN
			,NEW.LIC_VHC_CNT
			,NEW.SPR_VHC_CNT
			,NEW.APPL_ST_DT
			,NEW.APPL_ED_DT
			,NEW.WAY_DIV
			,NEW.USER_WAY_DIV
			,NEW.WAY_INFO
			,NEW.AREA
			,NEW.SHAPE_DIV
			,NEW.HOLI_YN
			,NEW.USE_YN
			,NEW.DEL_YN
			,NEW.REMARK
			,NEW.UPD_ID
				);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 트리거 sbrt.BMS_STTN_MST_after_update 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `sbrt`.`BMS_STTN_MST_after_update` AFTER UPDATE ON `sbrt`.`BMS_STTN_MST`
  FOR EACH ROW
BEGIN
INSERT INTO BMS_STTN_HIS(
			BMS_STTN_HIS.UPD_DTM
			,BMS_STTN_HIS.STTN_ID
			,BMS_STTN_HIS.STTN_NM
			,BMS_STTN_HIS.STTN_ENM
			,BMS_STTN_HIS.AREA
			,BMS_STTN_HIS.STTN_NO
			,BMS_STTN_HIS.CENTER_YN
			,BMS_STTN_HIS.STTN_FCLT_TYPE
			,BMS_STTN_HIS.APPL_RDS
			,BMS_STTN_HIS.BAY_TYPE
			,BMS_STTN_HIS.BAY_LEN
			,BMS_STTN_HIS.LINE_CNT
			,BMS_STTN_HIS.APPL_ST_DT
			,BMS_STTN_HIS.APPL_ED_DT
			,BMS_STTN_HIS.USE_YN
			,BMS_STTN_HIS.REMARK
			,BMS_STTN_HIS.UPD_ID)
	VALUE(
			NEW.UPD_DTM
			,NEW.STTN_ID
			,NEW.STTN_NM
			,NEW.STTN_ENM
			,NEW.AREA
			,NEW.STTN_NO
			,NEW.CENTER_YN
			,NEW.STTN_FCLT_TYPE
			,NEW.APPL_RDS
			,NEW.BAY_TYPE
			,NEW.BAY_LEN
			,NEW.LINE_CNT
			,NEW.APPL_ST_DT
			,NEW.APPL_ED_DT
			,NEW.USE_YN
			,NEW.REMARK
			,NEW.UPD_ID
					);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 트리거 sbrt.BRT_ALLOC_PL_MST_after_update 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `sbrt`.`BRT_ALLOC_PL_MST_after_update` AFTER UPDATE ON `sbrt`.`BRT_ALLOC_PL_MST`
  FOR EACH ROW
BEGIN
INSERT INTO BRT_ALLOC_PL_MST_HIS(
			BRT_ALLOC_PL_MST_HIS.UPD_DTM
			,BRT_ALLOC_PL_MST_HIS.REP_ROUT_ID
			,BRT_ALLOC_PL_MST_HIS.ALLOC_NO
			,BRT_ALLOC_PL_MST_HIS.DAY_DIV
			,BRT_ALLOC_PL_MST_HIS.VHC_ID
			,BRT_ALLOC_PL_MST_HIS.WAY_DIV
			,BRT_ALLOC_PL_MST_HIS.UPD_ID
			)
	VALUE(
				NEW.UPD_DTM
			,NEW.REP_ROUT_ID
			,NEW.ALLOC_NO
			,NEW.DAY_DIV
			,NEW.VHC_ID
			,NEW.WAY_DIV
			,NEW.UPD_ID
	);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 트리거 sbrt.BRT_CUR_CHG_OPER_DTL_INFO_after_update 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `sbrt`.`BRT_CUR_CHG_OPER_DTL_INFO_after_update` AFTER UPDATE ON `sbrt`.`BRT_CUR_CHG_OPER_DTL_INFO`
  FOR EACH ROW
BEGIN
INSERT INTO BRT_CUR_CHG_OPER_DTL_INFO_HIS(
			BRT_CUR_CHG_OPER_DTL_INFO_HIS.OCR_DTM
			,BRT_CUR_CHG_OPER_DTL_INFO_HIS.OPER_DT
			,BRT_CUR_CHG_OPER_DTL_INFO_HIS.REP_ROUT_ID
			,BRT_CUR_CHG_OPER_DTL_INFO_HIS.ROUT_ID
			,BRT_CUR_CHG_OPER_DTL_INFO_HIS.COR_ID
			,BRT_CUR_CHG_OPER_DTL_INFO_HIS.ALLOC_NO
			,BRT_CUR_CHG_OPER_DTL_INFO_HIS.OPER_SN
			,BRT_CUR_CHG_OPER_DTL_INFO_HIS.NODE_ID
			,BRT_CUR_CHG_OPER_DTL_INFO_HIS.NODE_SN
			,BRT_CUR_CHG_OPER_DTL_INFO_HIS.NODE_TYPE
			,BRT_CUR_CHG_OPER_DTL_INFO_HIS.DPRT_TM
			,BRT_CUR_CHG_OPER_DTL_INFO_HIS.ARRV_TM
			,BRT_CUR_CHG_OPER_DTL_INFO_HIS.UPD_DTM)
	VALUE(
			NEW.OCR_DTM
			,NEW.OPER_DT
			,NEW.REP_ROUT_ID
			,NEW.ROUT_ID
			,NEW.COR_ID
			,NEW.ALLOC_NO
			,NEW.OPER_SN
			,NEW.NODE_ID
			,NEW.NODE_SN
			,NEW.NODE_TYPE
			,NEW.DPRT_TM
			,NEW.ARRV_TM
			,NEW.UPD_DTM
	);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 트리거 sbrt.BRT_CUR_CHG_OPER_INFO_after_update 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `sbrt`.`BRT_CUR_CHG_OPER_INFO_after_update` AFTER UPDATE ON `sbrt`.`BRT_CUR_CHG_OPER_INFO`
  FOR EACH ROW
BEGIN
INSERT INTO BRT_CUR_CHG_OPER_INFO_HIS(
			BRT_CUR_CHG_OPER_INFO_HIS.OCR_DTM
			,BRT_CUR_CHG_OPER_INFO_HIS.OPER_DT
			,BRT_CUR_CHG_OPER_INFO_HIS.REP_ROUT_ID
			,BRT_CUR_CHG_OPER_INFO_HIS.ROUT_ID
			,BRT_CUR_CHG_OPER_INFO_HIS.ALLOC_NO
			,BRT_CUR_CHG_OPER_INFO_HIS.OPER_SN
			,BRT_CUR_CHG_OPER_INFO_HIS.VHC_ID
			,BRT_CUR_CHG_OPER_INFO_HIS.VHC_NO
			,BRT_CUR_CHG_OPER_INFO_HIS.OCR_LINK_ID
			,BRT_CUR_CHG_OPER_INFO_HIS.OCR_NODE_ID
			,BRT_CUR_CHG_OPER_INFO_HIS.OCR_NODE_TYPE
			,BRT_CUR_CHG_OPER_INFO_HIS.GPS_X
			,BRT_CUR_CHG_OPER_INFO_HIS.GPS_Y
			,BRT_CUR_CHG_OPER_INFO_HIS.TM_X
			,BRT_CUR_CHG_OPER_INFO_HIS.TM_Y
			,BRT_CUR_CHG_OPER_INFO_HIS.CHG_OPER_DIV
			,BRT_CUR_CHG_OPER_INFO_HIS.CHG_APPR_DIV
			,BRT_CUR_CHG_OPER_INFO_HIS.UPD_DTM)
	VALUE(
			NEW.OCR_DTM
			,NEW.OPER_DT
			,NEW.REP_ROUT_ID
			,NEW.ROUT_ID
			,NEW.ALLOC_NO
			,NEW.OPER_SN
			,NEW.VHC_ID
			,NEW.VHC_NO
			,NEW.OCR_LINK_ID
			,NEW.OCR_NODE_ID
			,NEW.OCR_NODE_TYPE
			,NEW.GPS_X
			,NEW.GPS_Y
			,NEW.TM_X
			,NEW.TM_Y
			,NEW.CHG_OPER_DIV
			,NEW.CHG_APPR_DIV
			,NEW.UPD_DTM
	);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 트리거 sbrt.BRT_CUR_OPER_ALLOC_PL_NODE_INFO_after_update 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `sbrt`.`BRT_CUR_OPER_ALLOC_PL_NODE_INFO_after_update` AFTER UPDATE ON `sbrt`.`BRT_CUR_OPER_ALLOC_PL_NODE_INFO`
  FOR EACH ROW
BEGIN
INSERT INTO BRT_CUR_OPER_ALLOC_PL_NODE_INFO_HIS(
			BRT_CUR_OPER_ALLOC_PL_NODE_INFO_HIS.UPD_DTM
			,BRT_CUR_OPER_ALLOC_PL_NODE_INFO_HIS.OPER_DT
			,BRT_CUR_OPER_ALLOC_PL_NODE_INFO_HIS.REP_ROUT_ID
			,BRT_CUR_OPER_ALLOC_PL_NODE_INFO_HIS.ROUT_ID
			,BRT_CUR_OPER_ALLOC_PL_NODE_INFO_HIS.COR_ID
			,BRT_CUR_OPER_ALLOC_PL_NODE_INFO_HIS.OPER_SN
			,BRT_CUR_OPER_ALLOC_PL_NODE_INFO_HIS.NODE_ID
			,BRT_CUR_OPER_ALLOC_PL_NODE_INFO_HIS.NODE_SN
			,BRT_CUR_OPER_ALLOC_PL_NODE_INFO_HIS.ALLOC_NO
			,BRT_CUR_OPER_ALLOC_PL_NODE_INFO_HIS.NODE_TYPE
			,BRT_CUR_OPER_ALLOC_PL_NODE_INFO_HIS.DPRT_TM
			,BRT_CUR_OPER_ALLOC_PL_NODE_INFO_HIS.ARRV_TM
			)
	VALUE(
			NEW.UPD_DTM
			,NEW.OPER_DT
			,NEW.REP_ROUT_ID
			,NEW.ROUT_ID
			,NEW.COR_ID
			,NEW.OPER_SN
			,NEW.NODE_ID
			,NEW.NODE_SN
			,NEW.ALLOC_NO
			,NEW.NODE_TYPE
			,NEW.DPRT_TM
			,NEW.ARRV_TM
	);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 트리거 sbrt.BRT_CUR_OPER_INFO_after_update 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `sbrt`.`BRT_CUR_OPER_INFO_after_update` AFTER UPDATE ON `sbrt`.`BRT_CUR_OPER_INFO`
  FOR EACH ROW
BEGIN


INSERT INTO BRT_CUR_OPER_HIS(
			BRT_CUR_OPER_HIS.OPER_DT
			,BRT_CUR_OPER_HIS.REP_ROUT_ID
			,BRT_CUR_OPER_HIS.VHC_ID
			,BRT_CUR_OPER_HIS.ROUT_ID
			,BRT_CUR_OPER_HIS.ALLOC_NO
			,BRT_CUR_OPER_HIS.OPER_SN
			,BRT_CUR_OPER_HIS.NODE_ID
			,BRT_CUR_OPER_HIS.COR_ID
			,BRT_CUR_OPER_HIS.VHC_NO
			,BRT_CUR_OPER_HIS.DRV_ID
			,BRT_CUR_OPER_HIS.GPS_X
			,BRT_CUR_OPER_HIS.GPS_Y
			,BRT_CUR_OPER_HIS.TM_X
			,BRT_CUR_OPER_HIS.TM_Y
			,BRT_CUR_OPER_HIS.OPER_STS
			,BRT_CUR_OPER_HIS.BUS_STS
			,BRT_CUR_OPER_HIS.OBE_STS
			,BRT_CUR_OPER_HIS.SNSTVTY
			,BRT_CUR_OPER_HIS.DRV_ANGLE
			,BRT_CUR_OPER_HIS.CUR_SPD
			,BRT_CUR_OPER_HIS.ACLRTN_YN
			,BRT_CUR_OPER_HIS.DCLRTN_YN
			,BRT_CUR_OPER_HIS.CUR_STOP_TM
			,BRT_CUR_OPER_HIS.OPER_LEN
			,BRT_CUR_OPER_HIS.REP_ROUT_NM
			,BRT_CUR_OPER_HIS.NODE_SN
			,BRT_CUR_OPER_HIS.NODE_TYPE
			,BRT_CUR_OPER_HIS.ARRV_TM
			,BRT_CUR_OPER_HIS.DPRT_TM
			,BRT_CUR_OPER_HIS.LINK_ID
			,BRT_CUR_OPER_HIS.LINK_SN
			,BRT_CUR_OPER_HIS.LINK_SPD
			,BRT_CUR_OPER_HIS.PRV_PLCE_NM
			,BRT_CUR_OPER_HIS.GET_OFF_CNT
			,BRT_CUR_OPER_HIS.PSG_CNT
			,BRT_CUR_OPER_HIS.NEXT_NODE_ID
			,BRT_CUR_OPER_HIS.NEXT_NODE_ARRV_TM
			,BRT_CUR_OPER_HIS.DOOR_CD
			,BRT_CUR_OPER_HIS.DOOR_TM
			,BRT_CUR_OPER_HIS.UPD_DTM)
	VALUE(
			NEW.OPER_DT
			,NEW.REP_ROUT_ID
			,NEW.VHC_ID
			,NEW.ROUT_ID
			,NEW.ALLOC_NO
			,NEW.OPER_SN
			,NEW.NODE_ID
			,NEW.COR_ID
			,NEW.VHC_NO
			,NEW.DRV_ID
			,NEW.GPS_X
			,NEW.GPS_Y
			,NEW.TM_X
			,NEW.TM_Y
			,NEW.OPER_STS
			,NEW.BUS_STS
			,NEW.OBE_STS
			,NEW.SNSTVTY
			,NEW.DRV_ANGLE
			,NEW.CUR_SPD
			,NEW.ACLRTN_YN
			,NEW.DCLRTN_YN
			,NEW.CUR_STOP_TM
			,NEW.OPER_LEN
			,NEW.REP_ROUT_NM
			,NEW.NODE_SN
			,NEW.NODE_TYPE
			,NEW.ARRV_TM
			,NEW.DPRT_TM
			,NEW.LINK_ID
			,NEW.LINK_SN
			,NEW.LINK_SPD
			,NEW.PRV_PLCE_NM
			,NEW.GET_OFF_CNT
			,NEW.PSG_CNT
			,NEW.NEXT_NODE_ID
			,NEW.NEXT_NODE_ARRV_TM
			,NEW.DOOR_CD
			,NEW.DOOR_TM
			,NEW.UPD_DTM
	);
	
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 트리거 sbrt.BRT_INCDNT_INFO_after_update 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `sbrt`.`BRT_INCDNT_INFO_after_update` AFTER UPDATE ON `sbrt`.`BRT_INCDNT_INFO`
  FOR EACH ROW
BEGIN
INSERT INTO BRT_INCDNT_HIS(
			BRT_INCDNT_HIS.OCR_DTM
			,BRT_INCDNT_HIS.OPER_DT
			,BRT_INCDNT_HIS.REP_ROUT_ID
			,BRT_INCDNT_HIS.VHC_ID
			,BRT_INCDNT_HIS.UPD_DTM
			,BRT_INCDNT_HIS.INCDNT_TYPE
			,BRT_INCDNT_HIS.ROUT_ID
			,BRT_INCDNT_HIS.ALLOC_NO
			,BRT_INCDNT_HIS.INCDNT_DETAIL
			,BRT_INCDNT_HIS.OPER_SN
			,BRT_INCDNT_HIS.COR_ID
			,BRT_INCDNT_HIS.VHC_NO
			,BRT_INCDNT_HIS.DRV_ID
			,BRT_INCDNT_HIS.REP_ROUT_NM
			,BRT_INCDNT_HIS.ROUT_NM
			,BRT_INCDNT_HIS.LINK_ID
			,BRT_INCDNT_HIS.NODE_ID
			,BRT_INCDNT_HIS.GPS_X
			,BRT_INCDNT_HIS.GPS_Y
			,BRT_INCDNT_HIS.TM_X
			,BRT_INCDNT_HIS.TM_Y
			,BRT_INCDNT_HIS.REMARK)
	VALUE(
			NEW.OCR_DTM
			,NEW.OPER_DT
			,NEW.REP_ROUT_ID
			,NEW.VHC_ID
			,UPD_DTM
			,NEW.INCDNT_TYPE
			,NEW.ROUT_ID
			,NEW.ALLOC_NO
			,NEW.INCDNT_DETAIL
			,NEW.OPER_SN
			,NEW.COR_ID
			,NEW.VHC_NO
			,NEW.DRV_ID
			,NEW.REP_ROUT_NM
			,NEW.ROUT_NM
			,NEW.LINK_ID
			,NEW.NODE_ID
			,NEW.GPS_X
			,NEW.GPS_Y
			,NEW.TM_X
			,NEW.TM_Y
			,NEW.REMARK
				);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 트리거 sbrt.BRT_OPER_ALLOC_PL_NODE_INFO_after_update 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `sbrt`.`BRT_OPER_ALLOC_PL_NODE_INFO_after_update` AFTER UPDATE ON `sbrt`.`BRT_OPER_ALLOC_PL_NODE_INFO`
  FOR EACH ROW
BEGIN
INSERT INTO BRT_OPER_ALLOC_PL_NODE_INFO_HIS(
			BRT_OPER_ALLOC_PL_NODE_INFO_HIS.UPD_DTM
			,BRT_OPER_ALLOC_PL_NODE_INFO_HIS.REP_ROUT_ID
			,BRT_OPER_ALLOC_PL_NODE_INFO_HIS.DAY_DIV
			,BRT_OPER_ALLOC_PL_NODE_INFO_HIS.WAY_DIV
			,BRT_OPER_ALLOC_PL_NODE_INFO_HIS.OPER_SN
			,BRT_OPER_ALLOC_PL_NODE_INFO_HIS.NODE_ID
			,BRT_OPER_ALLOC_PL_NODE_INFO_HIS.NODE_SN
			,BRT_OPER_ALLOC_PL_NODE_INFO_HIS.ALLOC_NO
			,BRT_OPER_ALLOC_PL_NODE_INFO_HIS.ROUT_ID
			,BRT_OPER_ALLOC_PL_NODE_INFO_HIS.COR_ID
			,BRT_OPER_ALLOC_PL_NODE_INFO_HIS.NODE_TYPE
			,BRT_OPER_ALLOC_PL_NODE_INFO_HIS.ARRV_TM
			,BRT_OPER_ALLOC_PL_NODE_INFO_HIS.DPRT_TM
			,BRT_OPER_ALLOC_PL_NODE_INFO_HIS.UPD_ID)
	VALUE(
			NEW.UPD_DTM
			,NEW.REP_ROUT_ID
			,NEW.DAY_DIV
			,NEW.WAY_DIV
			,NEW.OPER_SN
			,NEW.NODE_ID
			,NEW.NODE_SN
			,NEW.ALLOC_NO
			,NEW.ROUT_ID
			,NEW.COR_ID
			,NEW.NODE_TYPE
			,NEW.ARRV_TM
			,NEW.DPRT_TM
			,NEW.UPD_ID
	);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 트리거 sbrt.BRT_OPER_ALLOC_PL_ROUT_INFO_after_update 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `sbrt`.`BRT_OPER_ALLOC_PL_ROUT_INFO_after_update` AFTER UPDATE ON `sbrt`.`BRT_OPER_ALLOC_PL_ROUT_INFO`
  FOR EACH ROW
BEGIN
INSERT INTO BRT_OPER_ALLOC_PL_ROUT_INFO_HIS(
			BRT_OPER_ALLOC_PL_ROUT_INFO_HIS.UPD_DTM
			,BRT_OPER_ALLOC_PL_ROUT_INFO_HIS.REP_ROUT_ID
			,BRT_OPER_ALLOC_PL_ROUT_INFO_HIS.DAY_DIV
			,BRT_OPER_ALLOC_PL_ROUT_INFO_HIS.WAY_DIV
			,BRT_OPER_ALLOC_PL_ROUT_INFO_HIS.OPER_SN
			,BRT_OPER_ALLOC_PL_ROUT_INFO_HIS.ALLOC_NO
			,BRT_OPER_ALLOC_PL_ROUT_INFO_HIS.ROUT_ID
			,BRT_OPER_ALLOC_PL_ROUT_INFO_HIS.COR_ID
			,BRT_OPER_ALLOC_PL_ROUT_INFO_HIS.ALLOC_NM
			,BRT_OPER_ALLOC_PL_ROUT_INFO_HIS.ROUT_ST_TM
			,BRT_OPER_ALLOC_PL_ROUT_INFO_HIS.ROUT_ED_TM
			,BRT_OPER_ALLOC_PL_ROUT_INFO_HIS.REST_TM
			,BRT_OPER_ALLOC_PL_ROUT_INFO_HIS.UPD_ID)
	VALUE(
			NEW.UPD_DTM
			,NEW.REP_ROUT_ID
			,NEW.DAY_DIV
			,NEW.WAY_DIV
			,NEW.OPER_SN
			,NEW.ALLOC_NO
			,NEW.ROUT_ID
			,NEW.COR_ID
			,NEW.ALLOC_NM
			,NEW.ROUT_ST_TM
			,NEW.ROUT_ED_TM
			,NEW.REST_TM
			,NEW.UPD_ID
	);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 트리거 sbrt.BRT_OPER_PL_MST_after_update 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `sbrt`.`BRT_OPER_PL_MST_after_update` AFTER UPDATE ON `sbrt`.`BRT_OPER_PL_MST`
  FOR EACH ROW
BEGIN
INSERT INTO BRT_OPER_PL_MST_HIS(
			BRT_OPER_PL_MST_HIS.UPD_DTM
			,BRT_OPER_PL_MST_HIS.REP_ROUT_ID
			,BRT_OPER_PL_MST_HIS.DAY_DIV
			,BRT_OPER_PL_MST_HIS.WAY_DIV
			,BRT_OPER_PL_MST_HIS.ST_STTN_ID
			,BRT_OPER_PL_MST_HIS.ED_STTN_ID
			,BRT_OPER_PL_MST_HIS.OPER_CNT
			,BRT_OPER_PL_MST_HIS.ALLOC_CNT
			,BRT_OPER_PL_MST_HIS.FST_TM
			,BRT_OPER_PL_MST_HIS.LST_TM
			,BRT_OPER_PL_MST_HIS.AM_PEAK_ST_TM
			,BRT_OPER_PL_MST_HIS.AM_PEAK_ED_TM
			,BRT_OPER_PL_MST_HIS.PM_PEAK_ST_TM
			,BRT_OPER_PL_MST_HIS.PM_PEAK_ED_TM
			,BRT_OPER_PL_MST_HIS.AM_PEAK
			,BRT_OPER_PL_MST_HIS.PM_PEAK
			,BRT_OPER_PL_MST_HIS.NONE_PEAK
			,BRT_OPER_PL_MST_HIS.UPD_ID)
	VALUE(
			NEW.UPD_DTM
			,NEW.REP_ROUT_ID
			,NEW.DAY_DIV
			,NEW.WAY_DIV
			,NEW.ST_STTN_ID
			,NEW.ED_STTN_ID
			,NEW.OPER_CNT
			,NEW.ALLOC_CNT
			,NEW.FST_TM
			,NEW.LST_TM
			,NEW.AM_PEAK_ST_TM
			,NEW.AM_PEAK_ED_TM
			,NEW.PM_PEAK_ST_TM
			,NEW.PM_PEAK_ED_TM
			,NEW.AM_PEAK
			,NEW.PM_PEAK
			,NEW.NONE_PEAK
			,NEW.UPD_ID
	);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 트리거 sbrt.BRT_OPER_PL_ROUT_INFO_after_update 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `sbrt`.`BRT_OPER_PL_ROUT_INFO_after_update` AFTER UPDATE ON `sbrt`.`BRT_OPER_PL_ROUT_INFO`
  FOR EACH ROW
BEGIN
INSERT INTO BRT_OPER_PL_ROUT_INFO_HIS(
			BRT_OPER_PL_ROUT_INFO_HIS.UPD_DTM
			,BRT_OPER_PL_ROUT_INFO_HIS.REP_ROUT_ID
			,BRT_OPER_PL_ROUT_INFO_HIS.DAY_DIV
			,BRT_OPER_PL_ROUT_INFO_HIS.WAY_DIV
			,BRT_OPER_PL_ROUT_INFO_HIS.OPER_SN
			,BRT_OPER_PL_ROUT_INFO_HIS.ROUT_ID
			,BRT_OPER_PL_ROUT_INFO_HIS.ROUT_ST_TM
			,BRT_OPER_PL_ROUT_INFO_HIS.ROUT_ED_TM
			,BRT_OPER_PL_ROUT_INFO_HIS.UPD_ID)
	VALUE(
			NEW.UPD_DTM
			,NEW.REP_ROUT_ID
			,NEW.DAY_DIV
			,NEW.WAY_DIV
			,NEW.OPER_SN
			,NEW.ROUT_ID
			,NEW.ROUT_ST_TM
			,NEW.ROUT_ED_TM
			,NEW.UPD_ID

	);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
