import './style.css';

const TableB = () => {

    // get table_b by running dedup.java
    const table_b = [["836 Cornfield Dr", "Arlington", "TX"],
     ["815 Cornfield Drive", "Arlington", "TX"],
      ["932 Danforth Pl", "Arlington", "TX"],
       ["205 Kalmia Dr", "Arlington", "TX"],
        ["406 Faircrest Dr", "Arlington", "TX"],
         ["305 Iberis Dr", "Arlington", "TX"],
          ["306 Iberis Dr", "Arlington", "TX"]]

    return(
        <>
        
            <h3>Table B</h3>
            <table>
                <thead>
                    <tr>
                        <th>Address</th>
                        <th>City</th>
                        <th>State</th>
                    </tr>
                </thead>
                <tbody>
                    {table_b.map((row, index) => (
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
export default TableB;