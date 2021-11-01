INSERT INTO sbrt.PLF_PLF_SCH_MST
  (PLF_ID, SVC_ID, JOB_CLS, JOB_NM, JOB_STS, JOB_ARGS, PERIOD, ST_DTM, LAST_DTM, NXT_DTM, USE_YN)
VALUES
  ('SB-2021-01', 'TA-BIS-01', 'kr.tracom.platform.bis.job.AirQualityJob', 'AirQualityJob', '', '', '0 10 * * * ?', NULL, NULL, NULL, 'Y');

INSERT INTO sbrt.PLF_PLF_SCH_MST
  (PLF_ID, SVC_ID, JOB_CLS, JOB_NM, JOB_STS, JOB_ARGS, PERIOD, ST_DTM, LAST_DTM, NXT_DTM, USE_YN)
VALUES
  ('SB-2021-01', 'TA-BIS-01', 'kr.tracom.platform.bis.job.DeviceLocationJob', 'DeviceLocationJob', '', '', '0/10 * * * * ?', NULL, NULL, NULL, 'N');

INSERT INTO sbrt.PLF_PLF_SCH_MST
  (PLF_ID, SVC_ID, JOB_CLS, JOB_NM, JOB_STS, JOB_ARGS, PERIOD, ST_DTM, LAST_DTM, NXT_DTM, USE_YN)
VALUES
  ('SB-2021-01', 'TA-BIS-01', 'kr.tracom.platform.bis.job.DeviceStatusJob', 'DeviceStatusJob', '', '', '0 0/30 * * * ?', NULL, NULL, NULL, 'N');

INSERT INTO sbrt.PLF_PLF_SCH_MST
  (PLF_ID, SVC_ID, JOB_CLS, JOB_NM, JOB_STS, JOB_ARGS, PERIOD, ST_DTM, LAST_DTM, NXT_DTM, USE_YN)
VALUES
  ('SB-2021-01', 'TA-BIS-01', 'kr.tracom.platform.bis.job.NewsJob', 'NewsJob', '', '', '0 10 4,16 * * ?', NULL, NULL, NULL, 'Y');

INSERT INTO sbrt.PLF_PLF_SCH_MST
  (PLF_ID, SVC_ID, JOB_CLS, JOB_NM, JOB_STS, JOB_ARGS, PERIOD, ST_DTM, LAST_DTM, NXT_DTM, USE_YN)
VALUES
  ('SB-2021-01', 'TA-BIS-01', 'kr.tracom.platform.bis.job.TestBisJob', 'TestBisJob', '', '', '0/10 * * * * ?', NULL, NULL, NULL, 'N');

INSERT INTO sbrt.PLF_PLF_SCH_MST
  (PLF_ID, SVC_ID, JOB_CLS, JOB_NM, JOB_STS, JOB_ARGS, PERIOD, ST_DTM, LAST_DTM, NXT_DTM, USE_YN)
VALUES
  ('SB-2021-01', 'TA-BIS-01', 'kr.tracom.platform.bis.job.WeatherJob', 'WeatherJob', '', '', '0 10 * * * ?', NULL, NULL, NULL, 'Y');

INSERT INTO sbrt.PLF_PLF_SCH_MST
  (PLF_ID, SVC_ID, JOB_CLS, JOB_NM, JOB_STS, JOB_ARGS, PERIOD, ST_DTM, LAST_DTM, NXT_DTM, USE_YN)
VALUES
  ('SB-2021-01', 'TA-BRT-01', 'kr.tracom.platform.brt.job.CurrentOperJob', 'CurrentOperJob', '', '', '0 30 03 * * ?', NULL, NULL, NULL, 'Y');

INSERT INTO sbrt.PLF_PLF_SCH_MST
  (PLF_ID, SVC_ID, JOB_CLS, JOB_NM, JOB_STS, JOB_ARGS, PERIOD, ST_DTM, LAST_DTM, NXT_DTM, USE_YN)
VALUES
  ('SB-2021-01', 'TA-BRT-01', 'kr.tracom.platform.brt.job.RefreshBusJob', 'RefreshBusJob', '', '', '* */10 * * * ?', NULL, NULL, NULL, 'Y');

INSERT INTO sbrt.PLF_PLF_SCH_MST
  (PLF_ID, SVC_ID, JOB_CLS, JOB_NM, JOB_STS, JOB_ARGS, PERIOD, ST_DTM, LAST_DTM, NXT_DTM, USE_YN)
VALUES
  ('SB-2021-01', 'TA-BRT-01', 'kr.tracom.platform.brt.job.TrafficInfoJob', 'LinkSpeedJob', '', '', '0 0/5 * * * ?', NULL, NULL, NULL, 'Y');

INSERT INTO sbrt.PLF_PLF_SCH_MST
  (PLF_ID, SVC_ID, JOB_CLS, JOB_NM, JOB_STS, JOB_ARGS, PERIOD, ST_DTM, LAST_DTM, NXT_DTM, USE_YN)
VALUES
  ('SB-2021-01', 'TA-COM-01', 'kr.tracom.platform.communication.job.TransactionJob', 'TransactionJob', '', '', '0/1 * * * * ?', NULL, NULL, NULL, 'Y');

