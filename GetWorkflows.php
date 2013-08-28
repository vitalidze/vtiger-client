<?php
        // operation to retrieve all workflows
        // put to include/Webservices/GetWorkflows.php
        // then run following SQL query to enable this operation:
        // INSERT INTO `vtiger_ws_operation` ( `name` , `handler_path` , `handler_method` , `type` , `prelogin` ) VALUES ( 'getworkflows', 'include/Webservices/GetWorkflows.php', 'vtws_getworkflows', 'GET', '0' );
        require_once 'modules/com_vtiger_workflow/VTWorkflowManager.inc';
        function vtws_getworkflows($user){
                $adb = PearDatabase::getInstance();
                $wms = new VTWorkflowManager($adb);
                return $wms->getWorkflows();
        }

?>