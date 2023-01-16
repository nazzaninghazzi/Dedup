import './style.css';

const TableA = () => {

    // get table_a by running dedup.java
    const table_a = [["836 Cornfield Dr", "Arlington", "TX"],
     ["819 Lovingham Dr", "Arlington", "TX"],
      ["5208 Rowcrop Dr", "Arlington", "TX"],
       ["905 Thistle Ridge Ct", "Arlington", "TX"],
        ["5826 Falconcrest Dr", "Arlington", "TX"],
         ["408 Valley Spring Dr", "Arlington", "TX"],
          ["815 Cornfield Dr", "Arlington", "TX"],
           ["306 Iberis Dr", "Arlington", "TX"]]

           return(
            <>
            
                <h3>Table A</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Address</th>
                            <th>City</th>
                            <th>State</th>
                        </tr>
                    </thead>
                    <tbody>
                        {table_a.map((row, index) => (
                                    <tr key={index}>
                                        <td>{row[0]}</td>
                                        <td>{row[1]}</td>
                                        <td>{row[2]}</td>
                                    </tr>
                                )
                            )}
    
                    </tbody>
               
            </table>
          </>
        )
}
export default TableA;