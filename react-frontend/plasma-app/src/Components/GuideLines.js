import React from 'react'
import {Card} from 'react-bootstrap'

class GuideLines extends React.Component {
    constructor() {
        super()
    }

    render() {
        return (
            <div>
                <Card>
                    <Card.Body>
                        <Card.Title>GuideLines</Card.Title>
                        <Card.Text>
                            <b>You CAN donate plasma if:</b>
                            <ul>
                                <li>You were tested positive for COVID-19.</li>
                                <li>You are between 18-60 years old.</li>
                            </ul>
                            <b>You CANNOT donate plasma if:</b>
                            <ul>
                                <li>Your weight is less than 50 kg.</li>
                                <li>You have ever been pregnant.</li>
                            </ul>
                        </Card.Text>
                    </Card.Body>
                </Card>
            </div>
        )
    }
}

export default GuideLines