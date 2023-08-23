`EmpID` varchar,`EmpName` varchar,`EmpLogin` varchar,`EmpPassword` varchar,`EmpDepID` varchar,`EmpOrder` varchar,`EmpCreateDate` varchar,`EmpTel` varchar,`EmpComputer` varchar,`EmpIPAddress` varchar,`EmpMailBox` varchar,`EmpMailPassword` varchar,`EmpDisk` varchar,`EmpIsDeleted` varchar,`EmpPageSize` varchar,`EmpThemesName` varchar,`EmpIsAgent` varchar,`EmpAttXml` varchar,`PupilCoeff` varchar,`ManageCoeff` varchar,`EmpManageNote` varchar,`DeleteDataeTime` varchar,`EmpSex` varchar,`EmpPositional` varchar,`EmpWorkStart` varchar,`PermissionToken` varchar,`EmpCardID` varchar



`BaseMachineID` int, `MachineFactory` int, `MachineNo` varchar, `MachineName` varchar, `MachineIP` varchar, `MachinePort` int, `MachineAddDate` date, `MachineRemarks` varchar, `MachineAddEmpID` int, `MachineResponsEmpID` int, `MachineLedgerXml` longtext, `ISWS` int



`ChangeID` int, `ChangeMachineID` int, `ChangeMachineRecordID` int, `ChangeRecordState` text , `ChangeStartTime` datetime, `ChangeEndTime` datetime, `ChangeRecordData` longtext , `ChangeHandleState` int



`EnvoId` int ,`BaseID` int ,`CO2` float ,`PM25` float ,`PM10` float ,`Temperature` float ,`Humidity` float ,`TVOC` float ,`CH2O` float ,`Smoke` float ,`InPutTime` timestamp


`MachineRecordID` int,`achineID` int,`achineRecordState` text ,`achineRecordData` longtext ,`achineRecordDate` datetime


 `ProduceRecordID` int,`ProduceMachineID` int,`ProduceCodeNumber` varchar,`ProduceStartWaitTime` datetime,`ProduceCodeStartTime` datetime,`ProduceCodeEndTime` datetime,`ProduceCodeCycleTime` int,`ProduceEndTime` datetime,`ProducePrgCode` longtext ,`ProduceTotalOut` int,`ProduceInspect` int

