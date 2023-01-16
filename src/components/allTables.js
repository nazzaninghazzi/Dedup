import {useState} from "react";
import TableA from "./tableA";
import TableB from "./tableB";
import TableC from "./tableC";

const AllTables = () => {

    
    const [remove, setRemove] = useState(false);
    const [text, setText] = useState("Remove duplicates");
    const handleClick = () => {
        remove ? setText("Remove duplicates") : setText("Show both tables");
        setRemove(!remove);
        window.scrollTo(0, 0);
    }

    const switchTables = () => {
        if(!remove){
            return(
                <>
                    <TableA/>
                    <TableB/>
                </>
            )
        }else{
            return (<TableC/>)
        }
    }

    return(
        <>
            {switchTables()}
            <button onClick={() => handleClick()}>{text}</button>
        </>
       
    )
}
export default AllTables;