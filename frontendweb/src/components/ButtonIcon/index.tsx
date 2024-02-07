import './style.css';
import { ReactComponent as ArrowIcon } from 'assets/images/arrow.svg';
const ButtonIcon = () =>{

    return (
    <div className='btn-container'>

        <div>
            <button className = "btn btn-primary">
                <h6>INICIE AGORA A SUA BUSCA</h6>
            </button>
        </div>
        <div className='btn-icon-container'>
            <ArrowIcon/>
        </div>
    </div>
 );
       
}
export default ButtonIcon;